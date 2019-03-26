package stackjava.com.sendsms;

import org.smslib.Library;
import org.smslib.OutboundMessage;
import org.smslib.Service;
import org.smslib.modem.SerialModemGateway;

public class SendSMS {
	public static void main(String[] args) throws Exception{
		
		System.out.println("Example: Send message from a serial gsm modem.");
		System.out.println(Library.getLibraryDescription());
		System.out.println("Version: " + Library.getLibraryVersion());
//		SerialModemGateway gateway = new SerialModemGateway("", "COM5", 9600, "", "");
		SerialModemGateway gateway = new SerialModemGateway("sms.modem@COM5", "COM5", 115200, null, null);
//		SerialModemGateway gateway = new SerialModemGateway(id, comPort, baudRate, manufacturer, model)
		gateway.setInbound(true);
		gateway.setOutbound(true);
		Service.getInstance().addGateway(gateway);
		Service.getInstance().startService();
		System.out.println();
		System.out.println("Modem Information:");
		System.out.println("  Manufacturer: " + gateway.getManufacturer());
		System.out.println("  Model: " + gateway.getModel());
		System.out.println("  Serial No: " + gateway.getSerialNo());
		System.out.println("  SIM IMSI: " + gateway.getImsi());
		System.out.println("  Signal Level: " + gateway.getSignalLevel() + " dBm");
		System.out.println("  Battery Level: " + gateway.getBatteryLevel() + "%");
		System.out.println();
		OutboundMessage msg = new OutboundMessage("+841644419043", "Hello from SMSLib!");
		Service.getInstance().sendMessage(msg);
		System.out.println(msg);
		System.out.println("Now Sleeping - Hit <enter> to terminate.");
		System.in.read();
		Service.getInstance().stopService();

	}
	
}
