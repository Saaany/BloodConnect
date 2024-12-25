package com.termproject.Clients;

import Utils.networkObjStream;
import com.termproject.Model.*;

import java.util.ArrayList;
import java.util.List;

public class ServerData {

    public static networkObjStream nc;
    static List rs;

    public static void manualInitialize(){
        nc = new networkObjStream("127.0.0.1", 33333);
    }

    //validation of user account
    public static User checkUserAccount(String email, String pass) {
        nc.write("signin#" + email + "#" + pass);
        String str = "";
        while (str == "") {
            str = String.valueOf(nc.read());
        }

        String[] splitCommand = str.split("#");

        User user = new User();

        if (splitCommand[0].equals("true")) {

            user.setUserId(Integer.parseInt(splitCommand[1]));
            user.setUserName(splitCommand[2]);
            user.setPhone(splitCommand[3]);
            user.setEmail(splitCommand[4]);
            user.setGender(splitCommand[5]);
            user.setBloodGroup(splitCommand[6]);
        }
        else{
            user.setUserName("none");
        }

        return user;
    }

    public static BBManager checkManagerAccount(String email, String pass) {
        nc.write("managersignin#" + email + "#" + pass);
        String str = "";
        while (str == "") {
            str = String.valueOf(nc.read());
        }

        String[] splitCommand = str.split("#");

        BBManager user = new BBManager();

        if (splitCommand[0].equals("true")) {

            user.setManagerId(Integer.parseInt(splitCommand[1]));
            user.setManagerName(splitCommand[2]);
            user.setPhone(splitCommand[3]);
            user.setEmail(splitCommand[4]);
            user.setBloodBankId(Integer.parseInt(splitCommand[5]));
        }
        else{
            user.setManagerName("none");
        }

        return user;
    }

    public static boolean checkUsername(String email) {
        nc.write("checkUsername#"+email);
        String str = "";
        while (str==""){
            str = String.valueOf(nc.read());
            System.out.println(str);
        }
        if(str.equals("true"))
            return true;
        else
            return false;
    }
    public static boolean checkManagerMail(String email) {
        nc.write("checkManagerMail#"+email);
        String str = "";
        while (str==""){
            str = String.valueOf(nc.read());
            System.out.println(str);
        }
        if(str.equals("true"))
            return true;
        else
            return false;
    }

    public static void createUsers(String firstname, String lastname,String phoneNo, String email,String gender,String bloodGroup, String password){
        nc.write("createUsers#"+firstname+"#"+lastname+"#"+phoneNo+"#"+email+"#"+gender+"#"+bloodGroup+"#"+password);
    }
    public static void createManagers(String name, String phone, String email, String password, String bb_name, String bb_city, String bb_thana, String bb_upazilla, String bb_district) {
        nc.write("createManagers#"+name+"#"+phone+"#"+email+"#"+password+"#"+bb_name+"#"+bb_city+"#"+bb_thana+"#"+bb_upazilla+"#"+bb_district);
    }

    public static void createBloodPost(int user_id, String bloodGroup, String amount, String location, String lastDate, String time, String phone,String patientInfo) {
        nc.write("createBloodPost#"+user_id+"#"+bloodGroup+"#"+amount+"#"+location+"#"+lastDate+"#"+time +"#"+phone +"#"+patientInfo);
    }

    public static void createEvent(int man_id, String date, String location, String startTime, String endTime, String eventInfo, String currentDate) {
        nc.write("createEvent#"+man_id+"#"+date+"#"+location+"#"+startTime+"#"+endTime+"#"+eventInfo+"#"+currentDate);
    }

    public static List<BloodPost> getBloodPostData() {
        nc.write("getBloodPostData#");

        String str = "";
        while (str==""){
            str = String.valueOf(nc.read());

        }

        String[] splitData = str.split("#");
        String user_name;
        String bloodGroup,amount,location,lastDate,time,phone,patientInfo;
        int user_id;

        List<BloodPost> bloodPosts = new ArrayList<>();

        for (int i = 0; i < splitData.length-1;i=i+9) {

            user_name = splitData[i];
            bloodGroup = splitData[i+1];
            amount = splitData[i+2];
            location = splitData[i+3];
            lastDate = splitData[i+ 4];
            time = splitData[i + 5];
            phone =splitData[i+ 6];
            patientInfo = splitData[i + 7];
            user_id = Integer.parseInt(splitData[i+8]);

            BloodPost bloodPost = new BloodPost(user_id,user_name,bloodGroup,amount,location,lastDate,time,phone,patientInfo);
            bloodPosts.add(bloodPost);
        }
        return bloodPosts;
    }

    public static List<BloodPost> getNotificationData(String userId, String blood) {

        nc.write("getNotificationData#"+userId+"#"+blood);

        String str = "";
        while (str==""){
            str = String.valueOf(nc.read());
        }

        String[] splitData = str.split("#");
        String user_name;
        String bloodGroup,amount,location,lastDate,time,phone,patientInfo;
        int user_id;

        List<BloodPost> bloodPosts = new ArrayList<>();

        for (int i = 0; i < splitData.length-1;i=i+9) {

            user_name = splitData[i];
            bloodGroup = splitData[i + 1];
            amount = splitData[i + 2];
            location = splitData[i + 3];
            lastDate = splitData[i + 4];
            time = splitData[i + 5];
            phone =splitData[i + 6];
            patientInfo = splitData[i + 7];
            user_id = Integer.parseInt(splitData[i+8]);

            BloodPost bloodPost = new BloodPost(user_id,user_name,bloodGroup,amount,location,lastDate,time,phone,patientInfo);
            bloodPosts.add(bloodPost);
        }
        return bloodPosts;
    }

    public static List<Event> getEventData() {
        nc.write("getEventsData#");

        String str = "";
        while (str==""){
            str = String.valueOf(nc.read());
        }

        String[] splitData = str.split("#");
        String event_id,man_id,date,location,start_time,end_time,event_info,phone,email,bb_name;

        List<Event> events = new ArrayList<>();

        for (int i = 0; i < splitData.length-1;i=i+10) {

            event_id = splitData[i];
            man_id = splitData[i + 1];
            date = splitData[i + 2].split(" ")[0];
            location = splitData[i + 3];
            start_time = splitData[i + 4];
            end_time = splitData[i + 5];
            event_info =splitData[i + 6];
            phone = splitData[i + 7];
            email = splitData[i+8];
            bb_name = splitData[i+9];

            Event event = new Event(event_id,man_id,date,location,start_time,end_time,event_info,phone,email,bb_name);

            events.add(event);
        }
        return events;
    }
    public static void createDonationPost(int user_id, String location, String date) {

        nc.write("createDonationPost#"+user_id+"#"+location+"#"+date);
    }

    public static List<BloodPost> getYourBloodPosts(int userId) {

        nc.write("getYourBloodPostData#"+userId);

        String str = "";
        while (str==""){
            str = String.valueOf(nc.read());
        }

        String[] splitData = str.split("#");
        String user_name;
        String bloodGroup,amount,location,lastDate,time,phone,patientInfo;
        int post_id;
        List<BloodPost> bloodPosts = new ArrayList<>();

        for (int i = 0; i < splitData.length-1;i=i+9) {

            user_name = splitData[i];
            bloodGroup = splitData[i+1];
            amount = splitData[i+2];
            location = splitData[i+3];
            lastDate = splitData[i+ 4];
            time = splitData[i + 5];
            phone =splitData[i+ 6];
            patientInfo = splitData[i + 7];
            post_id = Integer.parseInt(splitData[i+8]);

            BloodPost bloodPost = new BloodPost(userId,user_name,bloodGroup,amount,location,lastDate,time,phone,patientInfo);
            bloodPost.setPost_id(post_id);
            bloodPosts.add(bloodPost);
        }
        return bloodPosts;
    }

    public static BloodPost getBloodPost(int post_id) {
        nc.write("getBloodPost#"+post_id);

        String str = "";
        while (str==""){
            str = String.valueOf(nc.read());
        }

        String[] splitData = str.split("#");
        String bloodGroup,amount,location,lastDate,time,phone,patientInfo;
        BloodPost bloodPost = new BloodPost();

        int i = 0;

        bloodGroup = splitData[i];
        amount = splitData[i+1];
        location = splitData[i+2];
        lastDate = splitData[i+3];
        time = splitData[i + 4];
        phone =splitData[i+ 5];
        patientInfo = splitData[i + 6];

        bloodPost.setAmount(amount);
        bloodPost.setLocation(location);
        bloodPost.setDate(lastDate);
        bloodPost.setTime(time);
        bloodPost.setPhone(phone);
        bloodPost.setPatientInfo(patientInfo);
        bloodPost.setPost_id(post_id);
        bloodPost.setBloodGroup(bloodGroup);

        return bloodPost;
    }
    public static List<DonationPost> getYourDonationPosts(int userId) {

        nc.write("getYourDonationPostData#"+userId);

        String str = "";
        while (str==""){
            str = String.valueOf(nc.read());
        }

        String[] splitData = str.split("#");
        String user_name;
        String location,date;

        List<DonationPost> donations = new ArrayList<>();

        for (int i = 0; i < splitData.length-1;i=i+3) {

            user_name = splitData[i];
            location = splitData[i+1];
            date = splitData[i+2];

            DonationPost donationPost = new DonationPost(user_name,location,date);
            donations.add(donationPost);
        }
        return donations;

    }
    public static String getLastDonationDate(int userId) {
        nc.write("getLastDonationDate#"+userId);

        String str = "";
        while (str==""){
            str = String.valueOf(nc.read());
        }

        String[] splitData = str.split(" ");

        return splitData[0];
    }

    public static String[] getBloodCountData(int bb_id) {

        nc.write("getBloodCountData#"+bb_id);

        String str = "";
        while (str==""){
            str = String.valueOf(nc.read());
        }

        String[] splitData = str.split("#");

        return splitData;
    }

    public static String[] getBloodBankData(int manager_id) {

        nc.write("getBloodBankData#"+manager_id);

        String str = "";
        while (str==""){
            str = String.valueOf(nc.read());
            //System.out.println(str);
        }

        String[] splitData = str.split("#");
        //System.out.println(splitData);
        return splitData;
    }

    public static User getUser(int user_id) {
        nc.write("getUser#"+user_id);

        String str = "";
        while (str == "") {
            str = String.valueOf(nc.read());
        }

        String[] splitCommand = str.split("#");

        User user = new User();

        if (splitCommand[0].equals("true")) {

            user.setUserId(Integer.parseInt(splitCommand[1]));
            user.setUserName(splitCommand[2]);
            user.setPhone(splitCommand[3]);
            user.setEmail(splitCommand[4]);
            user.setGender(splitCommand[5]);
            user.setBloodGroup(splitCommand[6]);
        }
        else{
            user.setUserName("none");
        }

        return user;
    }

    public static void updateBloodCountData(String updateData) {
        nc.write("updateBloodCountData#"+updateData);
    }

    public static List<BloodBank> getAllBloodBankByLocation(String searchLocation) {
        nc.write("getAllBloodBankByLocation#"+searchLocation);

        String str = "";
        while (str==""){
            str = String.valueOf(nc.read());
        }

        String[] splitData = str.split("#");
        String bb_name;
        String bb_id,man_id,location,man_phone,man_email;

        List<BloodBank> bloodBanks = new ArrayList<>();

        for (int i = 0; i < splitData.length-1;i=i+6) {

            bb_name = splitData[i];
            bb_id = splitData[i+1];
            man_id = splitData[i+2];
            location = splitData[i+3];
            man_phone = splitData[i+4];
            man_email = splitData[i+5];

            BloodBank bloodBank = new BloodBank(bb_name,bb_id,man_id,location,man_phone,man_email);
            bloodBanks.add(bloodBank);
        }
        return bloodBanks;
    }

    public static void CreateBloodRequestToBB(int userId, int bb_id, String blood, String amount,String date, String phone) {
        nc.write("bloodRequestToBB#"+userId+"#"+bb_id+"#"+blood+"#"+amount+"#"+phone+"#"+date);
    }

    public static List<BBRequestModel> getAllRequests(int bb_id) {

        nc.write("getAllRequests#"+bb_id);

        String str = "";
        while (str==""){
            str = String.valueOf(nc.read());
        }

        String[] splitData = str.split("#");
        String req_id,user_name,bloodGroup,amount,date,phone,status;

        List<BBRequestModel> bloodReqs = new ArrayList<>();

        for (int i = 0; i < splitData.length-1;i=i+7) {

            user_name = splitData[i];
            bloodGroup = splitData[i+1];
            amount = splitData[i+2];
            date = splitData[i+3];
            phone = splitData[i+4];
            status = splitData[i+5];
            req_id = splitData[i+6];

            BBRequestModel bbReq = new BBRequestModel(req_id,bb_id,user_name,bloodGroup,amount,date,phone,status);
            bloodReqs.add(bbReq);
        }
        return bloodReqs;
    }
    public static List<Event> getYourEvents(int man_id) {

        nc.write("getYourEvents#"+man_id);

        String str = "";
        while (str==""){
            str = String.valueOf(nc.read());
        }

        String[] splitData = str.split("#");
        String date,location,start_time,end_time,event_info,creation_date;

        List<Event> events = new ArrayList<>();

        for (int i = 0; i < splitData.length-1;i=i+6) {

            date = splitData[i];
            location = splitData[i+1];
            start_time = splitData[i+2];
            end_time = splitData[i+3];
            event_info = splitData[i+4];
            creation_date = splitData[i+5];

            Event event = new Event();
            event.setEventDate(date.split(" ")[0]);
            event.setLocation(location);
            event.setStartTime(start_time);
            event.setEndTime(end_time);
            event.setEventInfo(event_info);
            event.setCreation_date(creation_date.split(" ")[0]);

            events.add(event);
        }
        return events;
    }


    public static boolean updateBloodCountAfterApproval(int bloodBankId, int req_id, String blood_group, String amount, String status) {
        nc.write("updateBloodCountAfterApproval#"+bloodBankId+"#"+req_id+"#"+blood_group+"#"+amount+"#"+status);
        String str = "";
        while (str==""){
            str = String.valueOf(nc.read());
        }

        if(str.equalsIgnoreCase("true")){
            return true;
        }
        return false;
    }

    public static void editBloodPost(int post_id, String blood, String amount, String location, String date, String time, String phone, String patientInfo) {
        nc.write("updateBloodPost#"+post_id+"#"+blood+"#"+amount+"#"+location+"#"+date+"#"+time+"#"+phone+"#"+patientInfo);
    }

    public static void deleteYourBloodPost(int post_id) {
        nc.write("deleteYourBloodPost#"+post_id);
    }

    public void sendMessage(int sender,int receiver,String message){
        nc.write("sendMessage#"+sender+"#"+receiver+"#"+message);
    }

    public void getMessage(){

        String str = "";
        while (str == "") {
            str = String.valueOf(nc.read());
        }
    }


}
