package elastic.client;
import java.util.Date;
import java.util.UUID;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Introspected
public class HomeLabIndex {
    private String device;
    private String    signalType;
    private String    magnitude;           // (key word)
    private Date      creationTime;
    //private UUID      transactionId;
    private String    value;  // (key word)
    //private String    operationName;  // (key word)
    //private String    status;         // (key word)
    //private String    messages;
    //private boolean   markedAsRead;
   // private String    groups;
    //private UUID      notificationId;
}
