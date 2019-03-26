# java-send-sms-with-smslib
Here this article about how to create application:
https://stackjava.com/demo/gui-tin-nhan-sms-bang-java-su-dung-smslib.html

Gửi tin nhắn SMS bằng Java sử dụng smslib
Posted on Tháng Mười Một 23, 2017
Gửi tin nhắn SMS bằng Java sử dụng smslib.

Nhân dịp sắp tới mình có 1 task làm về gửi tin nhắn SMS mình tìm hiểu và code ví dụ 1 ứng dụng gửi tin nhắn SMS chia sẻ với mọi người =)).

1. Các cách gửi tin nhắn SMS trong Java
Mình tìm hiểu thì có 2 cách gửi tin nhắn SMS chính và hay sử dụng như sau:

Cách 1: Sử dụng API do bên thứ 3 cung cấp
Với cách này bạn đăng ký 1 tài khoản với nhà cung cấp như: twilio, nexmo

Sau khi có tài khoản, trong code Java bạn sẽ gửi request tới server của nhà cung cấp đó (request gồm account, tin sms cần gửi, số điện thoại đích) -> Nhà cung cấp dịch vụ sẽ xác định account hợp lệ không, nếu hợp lệ thì sẽ thực hiện gửi tin sms tới số điện thoại đích trong request.



Ưu điểm: Cách này khá dễ làm (các lib cho nhà cung cấp ngắn gọn, dễ hiểu, có hướng dẫn chi tiết, developer không cần quan tâm đến các thiết bị phần cứng, sóng mobile để gửi tin sms… chỉ cần 1 đường truyền internet là được)

Nhược điểm: mất phí =))

Cách 2: Sử dụng sim card của bạn để gửi tin nhắn sms.
Với cách này ta cần kết nối sim card tới ứng dụng Java qua các thiết bị như USB 3G/4G (dongle hay dcom)

Lắp sim card vào USB 3G/4G, kết nối USB 3G/4G vào máy tính.

Trong Java app, chúng ta sẽ kết nối tới USB 3G/4G và gửi lệnh yêu cầu nó gửi tin nhắn sms.



Ưu điểm: cách này free (sim vẫn mất tiền gửi sms) không mất phí dịch vụ, trả phí đúng như bạn nhắn tin trên điện thoại vậy. Nhắn nhiều hết nhiều, nhắn ngoại mạng cũng hết nhiều =))

Nhược điểm:

Cần các thiết bị phần cứng như sim card, USB 3G/4G (cái này cũng ko đắt lắm).
Không ổn định lắm do bạn phải quản lý phần cứng, chẳng hạn đúng lúc mình gọi lệnh gửi tin nhắn mà có thằng khác cũng truy cập tới cái USB 3G/4G đó thì fail.
Trường hợp bạn deploy web server thì không thể bảo nhà cung cấp server/host là “anh ơi lắp thêm cho em 1 cái USB 3G/4G vào server được không :o”
Cài đặt khó hơn cách thứ nhất, mình loay hoay cả tối mới tìm hiểu và code chạy được.
2. Gửi tin nhắn SMS bằng Java sử dụng smslib
Ở bài này mình sẽ hướng dẫn cách gửi tin nhắn sms theo cách 2.

Đầu tiên các bạn chuẩn bị 1 chiếc USB 3G/4G và 1 sim card cho phép gửi tin sms (1 số sim 3G/4G chỉ phục vụ cho việc truy cập internet nên ko gửi tin sms được; USB 3G/4G chỉ cần cho phép gửi tin sms là được, không cần phải kết nối mạng internet)

Đã cài JDK 6 hoặc các bản mới hơn.

Tải thư viện smslib tại đây (link dự phòng:  tại đây)

Config smslib như sau: giải nén folder smslib vừa tải về.

Copy RXTXcomm.jar vào folder:  %JAVA_HOME%/lib  và %JAVA_HOME%/jre/lib/ext
Copy rxtxParallel.dll và  rxtxSerial.dll vào folder: %JAVA_HOME%/bin và %JAVA_HOME%/jre/bin
Tạo project Java và thêm 2 thư viện smslib.java và log4j.jar vào project
(Các bạn có thể thay RXTXcomm.jar bằng comm.jar, thay rxtxParallel.dll và  rxtxSerial.dll bằng win32com.dll: tuy nhiên nó chỉ chạy được trên java 32bit)

%JAVA_HOME% là đường dẫn tới folder cài JDK

các file .dll này cho phép jvm kết nối với devices

Ví dụ mình cài jdk ở folder: C:\Program Files\Java\jdk1.8.0_131.

Lắp USB 3G/4G vào máy tính và kiểm tra port của nó:

Gửi tin nhắn SMS bằng Java sử dụng smslib

Gửi tin nhắn SMS bằng Java sử dụng smslib

Lưu ý, nhiều trường hợp bạn phải cắm USB 3G/4G vào máy tính + cài phần mềm đi kèm USB 3G/4G thì nó mới hiện lên mục Ports (COM & LPT) và thông tin của USB 3G/4G ở mục này.

Sau khi máy tính nhận port thì bạn kiểm tra USB 3G/4G nhận sim chưa, sau đó tắt các phần mềm đi kèm USB 3G/4G đó đi nếu không khi Java gửi lệnh tới nó sẽ báo lỗi là Port đang bị sử dụng bởi ứng dụng khác.

Sau khi tiến hành xong các bước trên, ta tiến hành code:

Code
System.out.println("stackjava.com: send sms by Java.");
System.out.println(Library.getLibraryDescription());
System.out.println("Version: " + Library.getLibraryVersion());
//SerialModemGateway gateway = new SerialModemGateway(id, comPort, baudRate, manufacturer, model)
SerialModemGateway gateway = new SerialModemGateway("model.com5", "COM5", 115200, null, null);
gateway.setInbound(true);
gateway.setOutbound(true);
Service.getInstance().addGateway(gateway);
Service.getInstance().startService();
System.out.println();
System.out.println("Thong tin modem:");
System.out.println("Nha san xuat: " + gateway.getManufacturer());
System.out.println("Model: " + gateway.getModel());
System.out.println("Serial No: " + gateway.getSerialNo());
System.out.println("SIM IMSI: " + gateway.getImsi());
System.out.println("Signal Level: " + gateway.getSignalLevel() + " dBm");
System.out.println();
String message = "stackjava.com \n demo send sms trong Java voi smslib";
// bạn thay xxx bằng số điện thoại người nhận
// lưu ý: số điện thoại có định dạng +mã quốc gia + sdt
// Ví dụ: số điện thoại của mình là 01644444444 thì mình sẽ để là +841644444444
String sdt = "xxx";
OutboundMessage msg = new OutboundMessage(sdt, message);
Service.getInstance().sendMessage(msg);
System.out.println(msg);
Service.getInstance().stopService();
System.out.println("Finish!");
Kết quả:
stackjava.com: send sms by Java.
SMSLib: A Java API library for sending and receiving SMS via a GSM modem or other supported gateways.
This software is distributed under the terms of the Apache v2.0 License.
Web Site: http://smslib.org
Version: 3.5.1
log4j:WARN No appenders could be found for logger (smslib).
log4j:WARN Please initialize the log4j system properly.
log4j:WARN See http://logging.apache.org/log4j/1.2/faq.html#noconfig for more info.

Thong tin modem:
Nha san xuat: +CGMI: MTK1
 Model: +CGMM: MTK2
 Serial No: 352247046996717
SIM IMSI: ** MASKED **
Signal Level: -67 dBm


===============================================================================
<< OutboundMessage >>
-------------------------------------------------------------------------------
 Gateway Id: null
 Message Id: 0
 Message UUID: 289099ca-5e58-4ea6-9778-3719bf18a87e
 Encoding: 7-bit
 Date: Thu Nov 23 18:01:15 SGT 2017
 SMSC Ref No: 16
 Recipient: +84xxxxxxxx
 Dispatch Date: Thu Nov 23 18:01:18 SGT 2017
 Message Status: SENT
 Failure Cause: NO_ERROR
 Validity Period (Hours): -1
 Status Report: false
 Source / Destination Ports: -1 / -1
 Flash SMS: false
 Text: stackjava.com 
 demo send sms trong Java voi smslib
 PDU data: 737A78BC5687ED61D7F8DD062940E472FB0D9A97DD64D0BC3D07D1E56FF719A40CDBC320FB3B0D9AB7E7ECB418
 Scheduled Delivery: null
===============================================================================

Finish!


Download source code tại đây

Lưu ý: hàm khởi tạo của SerialModemGateway là :

new SerialModemGateway(id, comPort, baudRate, manufacturer, model) trong đó quan trọng nhất là comPort phải đúng, còn id thì bạn đặt làm sao cho nó không bị trùng với các SerialModemGateway là được. Thông thường thì id, manufacturer và model có thể để null.

Thế là xong, bây giờ các bạn có thể áp dụng nó vào các hệ thống send sms veritify, spam sms (đùa thôi, đừng làm)… =))

Thanks các bạn đã theo dõi bài viết.

Để xem thêm các ví dụ khác, các bạn có thể truy cập: https://stackjava.com/category/demo

References:

http://gsmmodemtutoialbysamyan.blogspot.com/

http://kalssworld.blogspot.com/2015/01/sending-and-receiving-sms-using-your.html

http://codexamples.blogspot.com/2011/05/java-sms-send-read-with-smslib.html
