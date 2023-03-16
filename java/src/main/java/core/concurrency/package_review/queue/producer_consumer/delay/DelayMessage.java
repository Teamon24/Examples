package core.concurrency.package_review.queue.producer_consumer.delay;

import com.google.common.primitives.Ints;
import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

@Getter
public class DelayMessage implements Delayed {

    private String message;
    private long planedConsumptionTime;
    private long creationTime;

    public DelayMessage(String message, Long delayInMilliseconds) {
        this.message = message;
        this.creationTime = System.currentTimeMillis();
        this.planedConsumptionTime = this.creationTime + delayInMilliseconds;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        long diff = planedConsumptionTime - System.currentTimeMillis();
        return unit.convert(diff, TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed delayed) {
        long otherDelayed = ((DelayMessage) delayed).planedConsumptionTime;
        int saturatedCast = Ints.saturatedCast(this.planedConsumptionTime - otherDelayed);
        return saturatedCast;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("creationTime", new SimpleDateFormat("mm:ss:SS").format(Date.from(Instant.ofEpochMilli(creationTime))))
            .append("startTime", new SimpleDateFormat("mm:ss:SS").format(Date.from(Instant.ofEpochMilli(planedConsumptionTime))))
            .append("message", message)
            .toString();
    }
}
