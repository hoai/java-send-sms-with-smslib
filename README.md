# java-send-sms-with-smslib
Here this article about how to create application:
https://stackjava.com/demo/gui-tin-nhan-sms-bang-java-su-dung-smslib.html

Send SMS messages in Java using smslib

On the next occasion, I have a task to send SMS I find out and example code 1 application to send SMS to share =)).

### 1. Ways to send SMS in Java
I find out, there are 2 main ways to send SMS and use the following:

###### Method 1: Use API provided by 3rd party
This way you register an account with a provider like: twilio, nexmo

After having an account, in Java code you will send the request to the server of that provider (request including account, sms message to send, destination phone number) -> Service provider will determine if a valid account If valid, it will send sms to the destination phone number in the request.
![alt text](https://stackjava.com/wp-content/uploads/2017/11/java-send-sms-1.png "Logo Title Text 1")



Advantages: This method is quite easy to do (the provider lib is short, easy to understand, with detailed instructions, developers need not care about hardware devices, mobile waves to send sms messages ... only need 1 line internet transmission is okay)

Disadvantages: lost =))

###### Method 2: Use your sim card to send sms.
This way we need to connect sim card to Java application via devices like USB 3G / 4G (dongle or dcom)

Insert sim card into USB 3G / 4G, connect USB 3G / 4G to computer.

In Java app, we will connect to USB 3G / 4G and send the command asking it to send sms messages.
![alt text](https://stackjava.com/wp-content/uploads/2017/11/java-send-sms-2.png "Logo Title Text 1")


Advantage: this way is free (sim still loses sms deposit), there is no fee or service charge, just as you text on the phone. A lot of messages, many out-of-network messages =))

Defect:

Need hardware devices such as sim card, USB 3G / 4G (this is also not very expensive).
It's not very stable because you have to manage the hardware, for example, when I call the send command, another guy also accesses that 3G / 4G USB and fails.
In case you deploy the web server, it is impossible to tell the server / host provider, "Can you install me a USB 3G / 4G server on the server: o"
Installation is harder than the first one, I am struggling to find out the code and the code can run.
2. Send SMS in Java using smslib
In this article, I will guide how to send sms messages in the way 2.

First, you have to prepare 1 USB 3G / 4G and 1 sim card to send sms messages (some 3G / 4G sim cards are only for internet access, so no SMS messages can be sent; SMS messaging is possible, no internet connection required.

JDK 6 is installed or newer.

[Download the smslib library here](https://stackjava.com/wp-content/uploads/2017/11/smslib.zip)

Config smslib as follows: extract the downloaded smslib folder.

Copy RXTXcomm.jar into folder:  %JAVA_HOME%/lib  and %JAVA_HOME%/jre/lib/ext
Copy rxtxParallel.dll and  rxtxSerial.dll into folder: %JAVA_HOME%/bin and %JAVA_HOME%/jre/bin
Create Java project and add 2 smslib.java and log4j.jar libraries to the project
(You can replace RXTXcomm.jar with comm.jar, replace rxtxParallel.dll and rxtxSerial.dll with win32com.dll: but it can only run on 32bit java)

%JAVA_HOME% is the path to the JDK installation folder

These .dll files allow jvm to connect to devices

For example, I install jdk in folder: C:\Program Files\Java\jdk1.8.0_131.

Insert USB 3G / 4G into the computer and check its port:

![alt text](https://stackjava.com/wp-content/uploads/2017/11/java-sms-1.png "Logo Title Text 1")
![alt text](https://stackjava.com/wp-content/uploads/2017/11/java-sms-2.png "Logo Title Text 1")
Send SMS messages in Java using smslib

Send SMS messages in Java using smslib

Note, in many cases you have to plug USB 3G / 4G into the computer + install software with USB 3G / 4G, it will show up Ports (COM & LPT) and information of USB 3G / 4G in this section.

After the computer receives the port, you check the USB 3G / 4G to receive the sim, then turn off the software that comes with 3G 3G / 4G or if Java sends the command it will report that the Port is being used by other application.

After completing the above steps, we proceed with the code:
Code
```
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
```
Kết quả:
```
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
```
![alt text](https://stackjava.com/wp-content/uploads/2017/11/java-send-sms-3.png "Logo Title Text 1")

[Download source code here](http://www.mediafire.com/file/a9gfnr3s2tbt849/SendSMS.zip)

Note: SerialModemGateway's initialization function is:

new SerialModemGateway (id, comPort, baudRate, manufacturer, model) in which the most important is that the comPort must be correct, and the id you set is not to be identical to the SerialModemGateway. Usually id, manufacturer and model can be null.

That's it, now you can apply it to send sms systems veritify, spam sms (just kidding, don't do it) ... =))

Thanks for following the article.

To see more examples, you can visit: https://stackjava.com/category/demo

References:

http://gsmmodemtutoialbysamyan.blogspot.com/

http://kalssworld.blogspot.com/2015/01/sending-and-receiving-sms-using-your.html

http://codexamples.blogspot.com/2011/05/java-sms-send-read-with-smslib.html
