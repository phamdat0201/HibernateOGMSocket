package app;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import dao.DoctorDao;
import dao.PatientDao;
import entity.Doctor;
import entity.Patient;

public class Main {
	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(8900);
			System.out.println("Ready ...");
			while (true) {
				Socket socket = serverSocket.accept();
				System.out.println(socket.getInetAddress().getHostAddress());
				System.out.println("port -->" + socket.getPort());

				new Thread(new Handler(socket)).start();;
				System.out.println("Oke socket!");

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}

class Handler implements Runnable {
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private PatientDao patientDao;
	private DoctorDao doctorDao;

	public Handler(Socket socket) {
		this.socket = socket;
		patientDao = new PatientDao();
		doctorDao = new DoctorDao();
		System.out.println("constructor!");
	}

	@Override
	public void run() {
		try {
			in = new ObjectInputStream(socket.getInputStream());
			out = new ObjectOutputStream(socket.getOutputStream());
			System.out.println("alo");
			while (true) {
				Doctor doctor = (Doctor) in.readObject();
				System.out.println(doctor);
				boolean result = doctorDao.addDoctor(doctor);
				out.writeBoolean(result);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

}