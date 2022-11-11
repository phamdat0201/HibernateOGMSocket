package app;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashSet;

import entity.Doctor;

public class App {
	public static void main(String[] args) {
		try {
			Socket socket = new Socket("127.0.0.1", 8900);
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

			while (true) {
				Doctor doctor1 = new Doctor("DT100", "Steven King", new HashSet<>(Arrays.asList("(155) 555-2234")),
						new HashSet<String>(Arrays.asList("CCS-P", "CPCT", "CNA")), "Dermatologist");
				System.out.println(doctor1);
				out.writeObject(doctor1);
				boolean rs = in.readBoolean();
				System.out.println(rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
