package SC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

public class FactorPrimeServerMul {

	//	static String number = "4294967297";
	static String number = "1127451830576035879";
	//	static String number = "1607310476370097292596889203855070567269667934905795984956897118664324212127749670298953403271979017560"
	//			+ "9601429913262345458317707205045275551070134067328238564789969408388131619464241745157048346632778213573057"
	//			+ "556485618554648705303440456006343361472383645679026645743883162637555685413386695834981717272746246251646"
	//			+ "689847957440284107170390913806245656762456578425410156837840724227320766089203686970819068803335160153940"
	//			+ "1621576507964841597205952722487750670904522932328731530640706457382162644738538813247139315456213401586618"
	//			+ "8205178235764270941251970012703500878782708897174454011457922316740989484168888682501435920269738539737851"
	//			+ "2021707795176654693957752089724539218654727957249417768029150657850896270793487912491488088550072643962503"
	//			+ "3021936728949277390185399024276547035995915648938170415663757378637207011391538009596833354107737156273037"
	//			+ "4947278583020286633662969439250086473487692720355322650480497098272751793812528986759655285106192583767791"
	//			+ "71030556482884535728812916216625430187039533668677528079544176897647303445153643525354817413650848544778690"
	//			+ "688201005274443717680593899";


	public static void main(String[] args) throws IOException {


		ArrayList<Socket> socketList = new ArrayList<>();
		ArrayList<PrintWriter> pwList = new ArrayList<>();
		//		BufferedReader[] brList = new BufferedReader[3];
		ServerSocket serverSocket = new ServerSocket(4321);
		serverSocket.setSoTimeout(10000);
		Socket clientSocket = null;
		System.out.println("(... expecting connection ...)");
		while(true){
			try{
				clientSocket = serverSocket.accept();
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);                
				//	        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				socketList.add(clientSocket);
				pwList.add(out);
				//			brList[i] = in;
			}catch(SocketTimeoutException e){
				System.out.println("(... connection established ...)");
				break;
			}
		}

		for (int i=0;i<socketList.size();i++){        	
			pwList.get(i).print(number + " " + i + "\r\n");
			pwList.get(i).flush();
			pwList.get(i).close();
		}
		for (int i = 0; i < socketList.size(); i++) {
			socketList.get(i).close();
		}                
	}

}
