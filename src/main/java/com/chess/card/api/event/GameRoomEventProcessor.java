package com.chess.card.api.event;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;


@Slf4j
@Service
public class GameRoomEventProcessor {
    private final ConcurrentHashMap<String, Disruptor<RoomEvent>> roomDisruptors = new ConcurrentHashMap<>();

    @Autowired
    private LookerUserEveentHandler lookerUserEveentHandler;

    @Autowired
    private RoomDbEventHandler roomDbEventHandler;

    @Autowired
    private RoomUserEventHandler roomUserEventHandler;

    static final AtomicInteger THREAD_INIT_NUMBER = new AtomicInteger(1);

    private final int bufferSize = 1024;

    private String getName(Runnable r) {

        String domainEventHandlerName = "";

        if (r instanceof BatchEventProcessor) {
            try {
                Field eventHandler = BatchEventProcessor.class.getDeclaredField("eventHandler");
                eventHandler.setAccessible(true);
                Object o = eventHandler.get(r);

                if (o instanceof RoomUserEventHandler) {
                    domainEventHandlerName = ((RoomUserEventHandler)o).getName();
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                log.error(e.getMessage(), e);
            }

            return domainEventHandlerName;
        }

        return domainEventHandlerName;
    }
    private ThreadFactory createThreadFactory(String  roomId) {
        return r -> {
            String domainEventHandlerName = getName(r);

            List<String> nameParamList = new ArrayList<>();
            // 线程名前缀
            nameParamList.add("chess");
            // 主题名
            nameParamList.add(roomId);
            // 领域事件名
            nameParamList.add(domainEventHandlerName);
            // 编号
            nameParamList.add(String.valueOf(THREAD_INIT_NUMBER.getAndIncrement()));
            // 组合成线程名
            String threadName = String.join("-", nameParamList);
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            thread.setName(threadName);

            return thread;
        };
    }


    public void startRoom(String roomId) {
        // 自定义线程工厂
        ThreadFactory threadFactory = createThreadFactory(roomId);

        WaitStrategy waitStrategy = new LiteBlockingWaitStrategy();

        ProducerType producerType = ProducerType.MULTI;

        Disruptor<RoomEvent> disruptor = new Disruptor<>(RoomEvent::new, bufferSize, threadFactory, producerType, waitStrategy);

        disruptor.handleEventsWith(this.roomDbEventHandler,this.roomUserEventHandler,this.lookerUserEveentHandler);

        disruptor.start();

        roomDisruptors.put(roomId, disruptor);
    }



    /**
     * 退出房间
     * @param roomId
     */
    public void stopRoom(String roomId) {
        Disruptor<RoomEvent> disruptor = roomDisruptors.remove(roomId);
        if (disruptor != null) {
            disruptor.shutdown();
        }
        if(roomDisruptors.containsKey(roomId)){
            roomDisruptors.remove(roomId);
        }
    }



    private RingBuffer<RoomEvent> getRingBuffer(String roomId) {
        Disruptor<RoomEvent> disruptor = roomDisruptors.get(roomId);
        if (disruptor == null) {
            throw new IllegalStateException("Room " + roomId + " is not started");
        }
        return disruptor.getRingBuffer();
    }


    public void sendMessage(String roomId, Object message,EventType eventType) {
        RingBuffer<RoomEvent>  ringBuffer = getRingBuffer(roomId);
        if(ringBuffer==null){
            return;
        }
        long sequence = ringBuffer.next();
        try {
            RoomEvent event = ringBuffer.get(sequence);
            event.setRoomId(roomId);
            event.setEventType(eventType);
            event.setMessage(message);
        } finally {
            ringBuffer.publish(sequence);
        }
    }
}

