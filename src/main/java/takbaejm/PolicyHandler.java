package takbaejm;

import takbaejm.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{

    @Autowired
    DeliveryRepository deliveryRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPaid_DeliverySetPol(@Payload Paid paid){

        if(paid.isMe()){
            Delivery delivery = new Delivery();
            delivery.setRequestId(paid.getRequestId());
            delivery.setStatus("BeforeChecked");
            delivery.setLocation("-");
            delivery.setMemberId(paid.getMemberId());
            deliveryRepository.save(delivery);
            System.out.println("##### listener DeliverySetPol : " + paid.toJson());
        }
    }

}
