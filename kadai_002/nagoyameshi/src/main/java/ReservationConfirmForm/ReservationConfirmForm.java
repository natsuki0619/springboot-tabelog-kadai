package ReservationConfirmForm;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReservationConfirmForm {

	private Integer shopeId;

	private Integer userId;

	private Date reservationDate;

	private String reservationTime;

	private Integer numberOfPeople;

	private String remarks;

}
