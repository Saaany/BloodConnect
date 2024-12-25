package com.termproject.Server;

import Utils.Information;
import Utils.networkObjStream;
import com.termproject.ServerMain;

import java.net.Socket;
import java.util.HashMap;


public class ServerThread implements Runnable {

    private Socket userSocket;
    private Thread t;
    String Command;
    public static float rm;
    private static boolean exit = false;

    HashMap<Integer, Information> clientList = new HashMap<Integer, Information>();

    public ServerThread(Socket userSocket) {
        this.userSocket = userSocket;
        exit = false;
        t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {

        try {
            networkObjStream userStream = new networkObjStream(userSocket);
            System.out.println("user :"+ServerMain.activeUsersCount);
            while (Command == null) {

                Command = (String) userStream.read();

                //System.out.println(Command);
                String[] splitCommand = Command.split("#");

                if(splitCommand[0].equals("signin")) {
                    String returnString = DatabaseData.checkUserAccount(splitCommand[1], splitCommand[2]);
                    userStream.write(returnString);
                    Command = null;
                }
                if(splitCommand[0].equals("managersignin")) {
                    String returnString = DatabaseData.checkManagerAccount(splitCommand[1], splitCommand[2]);

                    userStream.write(returnString);
                    Command = null;
                }

                if(splitCommand[0].equals("checkUsername")) {
                    //System.out.println("yeeeeeeeeh");
                    boolean flag = DatabaseData.checkUsername(splitCommand[1]);//check email
                    //System.out.println("again yeeeh");
                    if (flag == false) {
                        userStream.write("false");
                    } else userStream.write("true");
                    Command = null;
                    System.out.println(flag);
                }
                if(splitCommand[0].equals("checkManagerMail")){
                    boolean flag = DatabaseData.checkManagerMail(splitCommand[1]);//check email
                    //System.out.println("again yeeeh");
                    if (flag == false) {
                        userStream.write("false");
                    } else userStream.write("true");
                    Command = null;
                    System.out.println(flag);
                }
                if(splitCommand[0].equals("createUsers")) {
                    DatabaseData.createUsers(splitCommand[1], splitCommand[2], splitCommand[3], splitCommand[4], splitCommand[5],splitCommand[6],splitCommand[7]);
                    Command = null;
                }
                if(splitCommand[0].equals("createManagers")) {
                    DatabaseData.createManagers(splitCommand[1], splitCommand[2], splitCommand[3], splitCommand[4], splitCommand[5],splitCommand[6],splitCommand[7],splitCommand[8],splitCommand[9]);
                    Command = null;
                }
                if(splitCommand[0].equals("createBloodPost")){
                    DatabaseData.createBloodPost(splitCommand[1],splitCommand[2],splitCommand[3],splitCommand[4],splitCommand[5],splitCommand[6],splitCommand[7],splitCommand[8]);
                    Command = null;
                }
                if(splitCommand[0].equals("deleteYourBloodPost")){
                    DatabaseData.deleteYourBloodPost(splitCommand[1]);
                    Command = null;
                }
                if(splitCommand[0].equals("createEvent")){
                    DatabaseData.createEvent(splitCommand[1],splitCommand[2],splitCommand[3],splitCommand[4],splitCommand[5],splitCommand[6],splitCommand[7]);
                    Command = null;
                }
                if(splitCommand[0].equals("getBloodPostData")){
                    String data = DatabaseData.getBloodPostData();
                    userStream.write(data);
                    Command = null;
                    System.out.println(data);
                }
                if(splitCommand[0].equals("getNotificationData")){
                    String data = DatabaseData.getNotificationData(splitCommand[1],splitCommand[2]);
                    userStream.write(data);
                    Command = null;
                    System.out.println(data);
                }
                if(splitCommand[0].equals("getEventsData")){
                    String data = DatabaseData.getEventsData();
                    userStream.write(data);
                    Command = null;
                    System.out.println(data);
                }
                if(splitCommand[0].equals("createDonationPost")){
                    DatabaseData.createDonationPost(splitCommand[1],splitCommand[2],splitCommand[3]);
                    Command = null;
                }
                if(splitCommand[0].equals("getYourBloodPostData")){
                    String data = DatabaseData.getYourBloodPostData(splitCommand[1]);
                    userStream.write(data);
                    Command = null;
                    System.out.println(data);
                }
                if(splitCommand[0].equals("getBloodPost")){
                    String data = DatabaseData.getBloodPost(splitCommand[1]);
                    userStream.write(data);
                    Command = null;
                    System.out.println(data);
                }
                if (splitCommand[0].equals("getYourDonationPostData")){
                    String data = DatabaseData.getYourDonationPostData(splitCommand[1]);
                    userStream.write(data);
                    Command = null;
                    System.out.println(data);
                }
                if(splitCommand[0].equals("getLastDonationDate")){
                    String data = DatabaseData.getLastDonationDate(splitCommand[1]);
                    System.out.println(data);
                    userStream.write(data);
                    Command = null;
                }
                if (splitCommand[0].equals("getBloodCountData")){
                    String data = DatabaseData.getBloodCountData(splitCommand[1]);
                    userStream.write(data);
                    Command = null;
                    System.out.println(data);
                }
                if (splitCommand[0].equals("getBloodBankData")) {
                    String data = DatabaseData.getBloodBankData(splitCommand[1]);
                    userStream.write(data);
                    Command = null;
                    System.out.println(data);
                }
                if (splitCommand[0].equals("getAllRequests")) {
                    String data = DatabaseData.getAllRequests(splitCommand[1]);
                    userStream.write(data);
                    Command = null;
                    System.out.println(data);
                }
                if (splitCommand[0].equals("getYourEvents")) {
                    String data = DatabaseData.getYourEvents(splitCommand[1]);
                    userStream.write(data);
                    Command = null;
                    System.out.println(data);
                }
                if(splitCommand[0].equals("getAllBloodBankByLocation")){
                    String data = DatabaseData.getAllBloodBankByLocation(splitCommand[1]);
                    userStream.write(data);
                    Command = null;
                }
                if (splitCommand[0].equals("updateBloodCountData")) {
                    DatabaseData.updateBloodCountData(splitCommand[1],splitCommand[2],splitCommand[3],splitCommand[4],splitCommand[5],splitCommand[6],splitCommand[7],splitCommand[8],splitCommand[9]);
                    Command = null;
                }
                if (splitCommand[0].equals("updateBloodCountAfterApproval")) {
                    boolean value = DatabaseData.updateBloodCountAfterApproval(splitCommand[1],splitCommand[2],splitCommand[3],splitCommand[4],splitCommand[5]);
                    userStream.write(value);
                    Command = null;
                }
                if(splitCommand[0].equals("updateBloodPost")){
                    DatabaseData.updateBloodPost(splitCommand[1],splitCommand[2],splitCommand[3],splitCommand[4],splitCommand[5],splitCommand[6],splitCommand[7],splitCommand[8]);
                    Command = null;
                }
                if(splitCommand[0].equals("getUser")){
                    String data = DatabaseData.getUser(splitCommand[1]);
                    userStream.write(data);
                    Command = null;
                    System.out.println(data);
                }
                if(splitCommand[0].equals("bloodRequestToBB")){
                    DatabaseData.CreateBloodRequestToBB(splitCommand[1],splitCommand[2],splitCommand[3],splitCommand[4],splitCommand[5],splitCommand[6]);
                    Command = null;
                }
                if(splitCommand[0].equals("sendMessage")){

                    String sender_id = splitCommand[1];
                    int receiver_id = Integer.parseInt(splitCommand[2]);
                    String message = splitCommand[3];

                    while(clientList.get(receiver_id).nc == null){
                        wait();
                    }

                    String data = sender_id + message;
                    clientList.get(receiver_id).nc.write(data);
                    Command = null;
                }
            }
        } catch (Exception ex) {
            System.out.println("client Disconnected");
            ServerMain.activeUsersCount--;
            System.out.println("Active user also here: "+ServerMain.activeUsersCount);
            //ServerUI.btnActiveUsers.fire();
            //ServerUI.btnActiveBar.fire();

            System.out.println(ex.getCause() +"\n"+ex.getMessage());
        }
    }
}


