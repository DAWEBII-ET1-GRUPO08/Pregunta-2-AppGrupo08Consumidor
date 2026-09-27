package com.cibertec.appGrupo08Consumidor.listener;

import com.cibertec.appGrupo08Consumidor.config.RabbitMQConfig;
import com.cibertec.appGrupo08Consumidor.service.MergeSortService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Stream;

@Component
public class NumbersListener {

    @Autowired
    private MergeSortService mergeSortService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void recibirMensaje(String cadenaNumeros) throws InterruptedException {

        System.out.println("Mensaje recibido: " + cadenaNumeros);

        Integer[] integerArray = Stream.of(cadenaNumeros.split(";"))
                .map(String::trim)
                .map(Integer::parseInt)
                .toArray(Integer[]::new);

        // Pausa de 20 segundos antes de ordenar
        Thread.sleep(20000);

        Integer[] resultado = mergeSortService.sort(integerArray);

        System.out.println("Lista ordenada: " + Arrays.toString(resultado));
    }

}
