let maxDate = new Date();
 maxDate = maxDate.setMonth(maxDate.getMonth() + 3);

 flatpickr('#fromReservationDate', {
   locale: 'ja',
   minDate: 'today',
   dateFormat: "Y-m-d",
 });;

 flatpickr('#fromReservationTime', {
   enableTime: true,
   noCalendar: true,
   dateFormat: "H:i",
   time_24hr: true,
 });;