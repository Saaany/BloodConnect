package com.termproject.Server;

import com.termproject.ServerMain;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DatabaseData {

    public static String getMd5(String input) {
        try {

            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            //  of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static List getResults(ResultSet rs) throws SQLException {
        int columnCount = rs.getMetaData().getColumnCount();
        List results = new ArrayList();
        while (rs.next()) {
            String str = "";
            for (int i = 0; i < columnCount; i++) {
                str += rs.getObject(i + 1) + "#";
            }
            results.add(str);
        }
        return results;
    }

    public static String checkUserAccount(String userMail, String pass) throws SQLException, ClassNotFoundException {

        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();
        String p = getMd5(pass);

        System.out.printf("Query processing");

        String qry1 = "select * from \"USERS\" where \"email\" ='" + userMail + "' and \"password\" ='" + p + "'";

        st.execute(qry1);

        int uid = 0;
        String userData  = "";

        ResultSet rs = st.getResultSet();
        while (rs.next()) {
            uid = rs.getInt(1);

            if (uid == 0){
                userData +=  "false#";
            }else{
                userData +="true#";
                userData += uid +"#"+ rs.getString(2)+" "+rs.getString(3)+"#"+rs.getString(4)+"#"+rs.getString(5)+"#"+rs.getString(6)+"#"+ rs.getString(7);//user_id,name,phone,email,gender,bloodGroup
            }
        }


        st.close();
        conn.close();

        return userData;
    }
    public static String checkManagerAccount(String userMail, String pass) throws SQLException, ClassNotFoundException {
        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();
        String p = getMd5(pass);
        String qry1 = "select * from \"BB_MANAGER_INFO\" where \"Email\" ='" + userMail + "' and \"Password\" ='" + p + "'";
        st.execute(qry1);

        int uid = 0;
        String userData  = "";

        ResultSet rs = st.getResultSet();
        while (rs.next()) {
            uid = rs.getInt(1);

            if (uid == 0){
                userData +=  "false#";
            }else{
                userData +="true#";
                userData += uid +"#"+ rs.getString(2)+"#"+rs.getString(3)+"#"+rs.getString(4)+"#";//user_id,name,phone,email
            }
        }

        System.out.println("uid = "+uid);
        if(uid!=0){

            String qry2 = "select \"BB_ID\" from \"BLOOD_BANK\" where \"MAN_ID\" ='" +uid+ "'";
            st.execute(qry2);

            int bb_id = 0;
            ResultSet rs2 = st.getResultSet();

            while(rs2.next()){
               bb_id = rs2.getInt(1);
            }

            userData+=bb_id;//man_id,name,phone,mail,bb_id
            System.out.println("userData "+userData);
        }

        st.close();
        conn.close();

        return userData;
    }


    public static boolean checkUsername(String email) throws SQLException, ClassNotFoundException {
        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();

        //String qry1 = "select \"email\" from \"USERS\"";
        String qry1 = "select email_check('"+email+"') from USERS";//using function
        st.execute(qry1);
        ResultSet rs = st.getResultSet();

        while (rs.next()) {
            if(rs.getString(1).equals("1")){
                System.out.println("function output: "+rs.getString(1));
                return false;
            }
        }
        rs.close();
        st.close();
        conn.close();
        return true;
    }

    public static boolean checkManagerMail(String email) throws SQLException, ClassNotFoundException {
        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();

        //String qry1 = "select \"Email\" from \"BB_MANAGER_INFO\"";
        String qry1 = "select manager_email_check('"+email+"') from BB_MANAGER_INFO";//using function
        st.execute(qry1);
        ResultSet rs = st.getResultSet();

        while (rs.next()) {
            if(rs.getString(1).equals("1")){
                System.out.println("function output: "+rs.getString(1));
                return false;
            }
        }
        rs.close();
        st.close();
        conn.close();
        return true;
    }

    public static void createUsers(String firstname, String lastname,String phoneNo, String email,String gender,String bloodGroup, String password)
            throws SQLException, ClassNotFoundException {

        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();
        int uid = 0;
        st.execute("select max(\"USER_ID\") from \"USERS\" order by \"USER_ID\" ASC");
        ResultSet rs = st.getResultSet();
        while (rs.next())
            uid = rs.getInt(1);
        uid++;

        String pass = getMd5(password);
        String qry2 = "INSERT INTO \"USERS\" (\"USER_ID\", \"first_name\", \"last_name\",\"phoneNo\",\"email\",\"gender\",\"bloodGroup\" ,\"password\" ) VALUES('" + uid + "','" + firstname + "','" + lastname + "','" +phoneNo+"','"+ email + "','"+ gender+"','"+ bloodGroup+"','" + pass + "')";

        st.execute(qry2);
        st.close();
        conn.close();
    }

    public static void createManagers(String man_name, String phone, String email, String password, String bb_name, String bb_city, String bb_thana, String bb_upazilla, String bb_district) throws SQLException, ClassNotFoundException {

        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();
        Statement st1 = conn.createStatement();
        Statement st2 = conn.createStatement();
        Statement st3 = conn.createStatement();

        int uid = 0;
        st.execute("select max(\"ID\") from \"BB_MANAGER_INFO\" order by \"ID\" ASC");
        ResultSet rs = st.getResultSet();
        while (rs.next())
            uid = rs.getInt(1);
        uid++;

        String pass = getMd5(password);
        String qry2 = "INSERT INTO \"BB_MANAGER_INFO\" (\"ID\", \"Name\", \"Phone\",\"Email\",\"Password\") VALUES('" + uid + "','" + man_name + "','" + phone + "','" +email+"','"+ pass + "')";
        st.execute(qry2);

        int location_id = 0;
        st1.execute("select max(\"LOCATION_ID\") from \"BB_LOCATION\" order by \"LOCATION_ID\" ASC");
        ResultSet rs2 = st1.getResultSet();
        while (rs2.next())
            location_id = rs2.getInt(1);
        location_id++;

        String qry4 = "INSERT INTO \"BB_LOCATION\" (\"LOCATION_ID\", \"City\", \"Thana\",\"Upazilla\",\"District\") VALUES('" + location_id + "','" + bb_city + "','" + bb_thana + "','" +bb_upazilla+"','"+ bb_district + "')";
        st1.execute(qry4);

        int bb_id = 0;
        st2.execute("select max(\"BB_ID\") from \"BLOOD_BANK\" order by \"BB_ID\" ASC");
        ResultSet rs3 = st2.getResultSet();
        while (rs3.next())
            bb_id = rs3.getInt(1);
        bb_id++;

        String qry6 = "INSERT INTO \"BLOOD_BANK\" (\"BB_ID\", \"MAN_ID\", \"BB_NAME\",\"LOCATION_ID\") VALUES('" + bb_id + "','" + uid + "','" + bb_name + "','" +location_id+ "')";
        st2.execute(qry6);

        String qry7 = "INSERT INTO \"BB_BLOOD_INFO\" (\"BB_ID\",\"A+\", \"A-\", \"B+\",\"B-\",\"AB+\", \"AB-\", \"O+\",\"O-\") VALUES(" +bb_id+ ",0,0,0,0,0,0,0,0)";
        st3.execute(qry7);

        st.close();
        st1.close();
        st2.close();
        st3.close();
        conn.close();

    }

    public static int getTotalUsers() throws SQLException, ClassNotFoundException {
        int r = 0;
        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();

        st.execute("SELECT COUNT(USERS.USER_ID)\n" +
                "FROM USERS");
        ResultSet rs = st.getResultSet();
        while (rs.next()) {
            r = rs.getInt(1);
        }
        st.close();
        conn.close();
        return r;
    }

    public static void createBloodPost(String user_id, String bloodGroup, String amount, String location, String lastDate,String time, String phone, String patientInfo) throws SQLException, ClassNotFoundException {

        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();

        int post_id = 0;
        st.execute("select max(\"POST_ID\") from \"BLOOD_POSTS\"");
        ResultSet rs = st.getResultSet();
        while (rs.next())
            post_id = rs.getInt(1);
        post_id++;
        System.out.println("post id "+post_id);

        String status = "on";//todo:check time and date
        String qry2 = "INSERT INTO \"BLOOD_POSTS\" (\"POST_ID\", \"bloodGroup\", \"amount\",\"location\",\"lastDate\",\"time\",\"phone\",\"patientInfo\",\"USER_ID\",\"status\") VALUES('" + post_id + "','" + bloodGroup + "','" + amount + "','" + location + "',TO_DATE('" +lastDate+"','yyyy-mm-dd'),'"+time+"','"+ phone + "','"+ patientInfo+ "','"+user_id+"','"+status+"')";
        st.execute(qry2);
        System.out.println("qry 2 complete!");

        //todo: changes has been occurred
        st.close();
        conn.close();
    }

    public static void CreateBloodRequestToBB(String user_id, String bb_id, String blood, String amount, String phone, String date) throws SQLException, ClassNotFoundException {
        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();

        int req_id = 0;
        st.execute("select max(\"REQ_ID\") from \"USER_BB_BLOOD_REQUESTS\"");
        ResultSet rs = st.getResultSet();
        while (rs.next())
            req_id = rs.getInt(1);
        req_id++;

        String status = "Pending";//todo:check time and date
        String qry2 = "INSERT INTO \"USER_BB_BLOOD_REQUESTS\" (\"REQ_ID\", \"USER_ID\", \"BB_ID\",\"BloodGroup\",\"Amount\",\"Phone\",\"Date\",\"Status\") VALUES('" + req_id + "','" + user_id + "','" + bb_id + "','" + blood +"','"+amount+ "','"+phone+"',TO_DATE('" +date+"','yyyy-mm-dd'),'"+status+"')";
        st.execute(qry2);
        System.out.println("req complete!");

        st.close();
        conn.close();
    }

    public static void createEvent(String man_id, String date, String location, String startTime, String endTime, String eventInfo, String creation_date) throws SQLException, ClassNotFoundException {

        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();

        int event_id = 0;
        st.execute("select max(\"EVENT_ID\") from \"EVENTS\"");
        ResultSet rs = st.getResultSet();
        while (rs.next())
            event_id = rs.getInt(1);
        event_id++;

        String status = "on";//todo:check time and date
        String qry2 = "INSERT INTO \"EVENTS\" (\"EVENT_ID\",\"MAN_ID\",\"Date\", \"Location\",\"Start_Time\",\"End_Time\",\"Event_Info\",\"status\",\"creation_date\") VALUES('" + event_id + "','" + man_id + "',TO_DATE('" +date+"','yyyy-mm-dd'),'"+location+"','"+ startTime + "','"+ endTime+ "','"+eventInfo+"','"+status+"',TO_DATE('" +creation_date+"','yyyy-mm-dd'))";
        st.execute(qry2);

        st.close();
        conn.close();
    }

    public static String getBloodPostData() throws SQLException, ClassNotFoundException {

        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();

        /**
         * fixing date & time situation
         */

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now));
        String curr = dtf.format(now);

        String user_name,bloodGroup,amount,location,lastDate,time,phone,patientInfo;
        int user_id;
        String Data = "";

        st.execute("select (\"first_name\"||' '||\"last_name\") \"user\", b.\"bloodGroup\",b.\"amount\",b.\"location\",b.\"lastDate\",b.\"time\",b.\"phone\",b.\"patientInfo\",b.\"USER_ID\" from \"BLOOD_POSTS\" b, \"USERS\" c where b.USER_ID = c.USER_ID and b.\"lastDate\" >= TO_DATE('"+ curr + "', 'yyyy-mm-dd') and b.\"status\"='on' order by b.\"lastDate\" ASC" );

        ResultSet rs = st.getResultSet();
        while (rs.next()) {
            user_name = rs.getString(1);
            bloodGroup = rs.getString(2);
            amount = rs.getString(3);
            location = rs.getString(4);
            lastDate = rs.getDate(5).toString();
            time = rs.getString(6);
            phone = rs.getString(7);
            patientInfo = rs.getString(8);
            user_id = rs.getInt(9);

            Data +=  user_name+"#"+bloodGroup+"#"+amount+"#"+location+"#"+lastDate+"#"+time+"#"+phone+"#"+patientInfo+"#"+user_id+"#";
        }

        st.close();
        conn.close();

        return Data;
    }

    public static String getNotificationData(String userId, String blood) throws SQLException, ClassNotFoundException {

        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();

        /**
         * fixing date & time situation
         */

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now));
        String curr = dtf.format(now);

        String user_name,bloodGroup,amount,location,lastDate,time,phone,patientInfo;
        int user_id;
        String Data = "";

        st.execute("select (\"first_name\"||' '||\"last_name\") \"user\", b.\"bloodGroup\",b.\"amount\",b.\"location\",b.\"lastDate\",b.\"time\",b.\"phone\",b.\"patientInfo\",b.\"USER_ID\" from \"BLOOD_POSTS\" b, \"USERS\" c where c.user_id <> '"+ userId + "' and b.USER_ID = c.USER_ID and b.\"lastDate\" >= TO_DATE('"+ curr + "', 'yyyy-mm-dd') and b.\"bloodGroup\" = '"+blood+"' ORDER BY b.\"lastDate\" ASC" );

        ResultSet rs = st.getResultSet();
        while (rs.next()) {
            user_name = rs.getString(1);
            bloodGroup = rs.getString(2);
            amount = rs.getString(3);
            location = rs.getString(4);
            lastDate = rs.getDate(5).toString();
            time = rs.getString(6);
            phone = rs.getString(7);
            patientInfo = rs.getString(8);
            user_id = rs.getInt(9);

            Data +=  user_name+"#"+bloodGroup+"#"+amount+"#"+location+"#"+lastDate+"#"+time+"#"+phone+"#"+patientInfo+"#"+user_id+"#";
        }

        st.close();
        conn.close();

        return Data;
    }

    public static String getEventsData() throws SQLException, ClassNotFoundException {
        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();

        /**
         * fixing date & time situation
         */
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now));
        String curr = dtf.format(now);

        String event_id,man_id,date,location,start_time,end_time,phone,email,event_info,bb_name;
        String Data = "";

        st.execute("select a.\"EVENT_ID\", a.\"MAN_ID\",a.\"Date\",a.\"Location\",a.\"Start_Time\",a.\"End_Time\",a.\"Event_Info\",b.\"Phone\",b.\"Email\",c.\"BB_NAME\" from \"EVENTS\" a, \"BB_MANAGER_INFO\" b, \"BLOOD_BANK\" c where a.MAN_ID = b.ID and a.MAN_ID = c.MAN_ID and a.\"Date\">= TO_DATE('"+ curr + "', 'yyyy-mm-dd') order by a.\"Date\" ASC");//>= TO_CHAR(SYSDATE, 'yyyy-mm-dd') ORDER BY a.\"Date\" ASC);

        ResultSet rs = st.getResultSet();
        while (rs.next()) {
            event_id = rs.getString(1);
            man_id = rs.getString(2);
            date = rs.getString(3);
            location = rs.getString(4);
            start_time = rs.getString(5);
            end_time = rs.getString(6);
            event_info = rs.getString(7);
            phone = rs.getString(8);
            email = rs.getString(9);
            bb_name = rs.getString(10);

            Data +=  event_id+"#"+man_id+"#"+date+"#"+location+"#"+start_time+"#"+end_time+"#"+event_info+"#"+phone+"#"+email+"#"+bb_name+"#";
        }

        st.close();
        conn.close();

        return Data;
    }
    public static void createDonationPost(String user_id, String location, String date) throws SQLException, ClassNotFoundException {

        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();

        int post_id = 0;
        st.execute("select max(\"POST_ID\") from \"DONATION_POSTS\"");
        ResultSet rs = st.getResultSet();
        while (rs.next())
            post_id = rs.getInt(1);
        post_id++;
        System.out.println("post id "+post_id);

        String status = "on";//todo: check date & time
        String qry2 = "INSERT INTO \"DONATION_POSTS\" (\"POST_ID\", \"location\", \"date\",\"USER_ID\",\"status\") VALUES('" + post_id + "','" + location + "',TO_DATE('" +date+"','yyyy-mm-dd'),'"+user_id+"','"+status+"')";
        st.execute(qry2);

        st.close();
        conn.close();
    }

    public static String getYourBloodPostData(String userId) throws SQLException, ClassNotFoundException {

        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();

        String user_name,bloodGroup,amount,location,lastDate,time,phone,patientInfo;
        int post_id;
        String Data = "";

        st.execute("select (\"first_name\"||' '||\"last_name\") \"user\", b.\"bloodGroup\",b.\"amount\",b.\"location\",b.\"lastDate\",b.\"time\",b.\"phone\",b.\"patientInfo\",b.\"POST_ID\" from \"BLOOD_POSTS\" b, \"USERS\" c where c.user_id = '" + userId + "' and b.USER_ID = c.USER_ID and \"status\" = 'on' ORDER BY b.\"POST_ID\" desc" );

        ResultSet rs = st.getResultSet();
        while (rs.next()) {
            user_name = rs.getString(1);
            bloodGroup = rs.getString(2);
            amount = rs.getString(3);
            location = rs.getString(4);
            lastDate = rs.getDate(5).toString();
            time = rs.getString(6);
            phone = rs.getString(7);
            patientInfo = rs.getString(8);
            post_id = rs.getInt(9);

            Data +=  user_name+"#"+bloodGroup+"#"+amount+"#"+location+"#"+lastDate+"#"+time+"#"+phone+"#"+patientInfo+"#"+post_id+"#";
        }

        st.close();
        conn.close();

        return Data;
    }

    public static String getBloodPost(String post_id) throws SQLException, ClassNotFoundException {

        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();

        String bloodGroup,amount,location,lastDate,time,phone,patientInfo;
        String Data = "";

        st.execute("select b.\"bloodGroup\",b.\"amount\",b.\"location\",b.\"lastDate\",b.\"time\",b.\"phone\",b.\"patientInfo\",b.\"POST_ID\" from \"BLOOD_POSTS\" b where b.\"POST_ID\" = '"+post_id+"' and \"status\" = 'on'" );

        ResultSet rs = st.getResultSet();
        while (rs.next()) {
            bloodGroup = rs.getString(1);
            amount = rs.getString(2);
            location = rs.getString(3);
            lastDate = rs.getDate(4).toString();
            time = rs.getString(5);
            phone = rs.getString(6);
            patientInfo = rs.getString(7);

            Data += bloodGroup+"#"+amount+"#"+location+"#"+lastDate+"#"+time+"#"+phone+"#"+patientInfo+"#";
        }

        st.close();
        conn.close();

        return Data;
    }
    public static String getYourDonationPostData(String userId) throws SQLException, ClassNotFoundException {

        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();

        String user_name,location,date;
        String Data = "";

        st.execute("select (\"first_name\"||' '||\"last_name\") \"user\", b.\"location\",b.\"date\" from \"DONATION_POSTS\" b, \"USERS\" c where c.USER_ID = '" + userId + "' and b.USER_ID = c.USER_ID and \"status\" = 'on' ORDER BY b.\"date\" DESC");

        ResultSet rs = st.getResultSet();
        while (rs.next()) {
            user_name = rs.getString(1);
            location = rs.getString(2);
            date = rs.getString(3);

            Data +=  user_name+"#"+location+"#"+date+"#";
        }

        st.close();
        conn.close();

        return Data;
    }
    public static String getLastDonationDate(String user_id) throws SQLException, ClassNotFoundException {

        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();

        String lastDate;
        String Data = "";

        st.execute("select MAX(\"date\") from \"DONATION_POSTS\" where USER_ID = '" + user_id + "' GROUP BY USER_ID");

        ResultSet rs = st.getResultSet();
        while (rs.next()) {
            lastDate = rs.getString(1);
            Data += lastDate;
        }

        st.close();
        conn.close();

        return Data;
    }

    public static String getBloodBankData(String manager_id) throws SQLException, ClassNotFoundException {

        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();

        String bloodBankName,manName,location,bbId;
        String Data = "";

        st.execute("select b.\"BB_NAME\",c.\"Name\",(l.\"City\"||','||l.\"Thana\"||','||l.\"Upazilla\"||','||l.\"District\") \"location\" from \"BLOOD_BANK\" b, \"BB_MANAGER_INFO\" c, \"BB_LOCATION\" l where c.ID = '" + manager_id + "' and b.MAN_ID = c.ID and b.LOCATION_ID = l.LOCATION_ID");

        ResultSet rs = st.getResultSet();
        while (rs.next()) {
            bloodBankName = rs.getString(1);
            manName = rs.getString(2);
            location = rs.getString(3);

            Data +=  bloodBankName+"#"+manName+"#"+location;
        }

        st.close();
        conn.close();

        return Data;
    }
    public static String getAllBloodBankByLocation(String searchLocation) throws SQLException, ClassNotFoundException {

        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();

        String bloodBankName,man_id,location,bbId,man_phone,man_email;
        String Data = "";

        searchLocation = searchLocation.toUpperCase();

        st.execute("select b.\"BB_NAME\",b.\"BB_ID\",c.\"ID\",c.\"Phone\",c.\"Email\",(l.\"City\"||','||l.\"Thana\"||','||l.\"Upazilla\"||','||l.\"District\") \"location\" from \"BLOOD_BANK\" b, \"BB_MANAGER_INFO\" c, \"BB_LOCATION\" l where b.MAN_ID = c.ID and b.LOCATION_ID = l.LOCATION_ID");//UPPER(location) LIKE %" + searchLocation + "% and

        ResultSet rs = st.getResultSet();
        while (rs.next()) {
            bloodBankName = rs.getString(1);
            bbId = rs.getString(2);
            man_id = rs.getString(3);
            man_phone = rs.getString(4);
            man_email = rs.getString(5);
            location = rs.getString(6);

            if(location.toUpperCase().contains(searchLocation)){
                Data +=  bloodBankName+"#"+bbId+"#"+man_id+"#"+location+"#"+man_phone+"#"+man_email+"#";
            }
        }

        st.close();
        conn.close();

        return Data;
    }
    public static String getAllRequests(String bb_id) throws SQLException, ClassNotFoundException {

        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();

        String req_id,userName,bloodGroup,amount,date,phone,status;
        String Data = "";

        st.execute("select (b.\"first_name\" || b.\"last_name\") Name,c.\"BloodGroup\",c.\"Amount\",c.\"Date\",c.\"Phone\",c.\"Status\",c.\"REQ_ID\" from \"USERS\" b, \"USER_BB_BLOOD_REQUESTS\" c where c.BB_ID = '"+bb_id+"' and c.USER_ID = b.USER_ID and c.\"Status\"<>'rejected' ORDER BY \"Status\" DESC, \"Date\" ASC");

        ResultSet rs = st.getResultSet();
        while (rs.next()) {
            userName = rs.getString(1);
            bloodGroup = rs.getString(2);
            amount = rs.getString(3);
            date = rs.getString(4);
            phone = rs.getString(5);
            status = rs.getString(6);
            req_id = rs.getString(7);

            Data +=  userName+"#"+bloodGroup+"#"+amount+"#"+date+"#"+phone+"#"+status+"#"+req_id+"#";

        }

        st.close();
        conn.close();

        return Data;
    }
    public static String getYourEvents(String man_id) throws SQLException, ClassNotFoundException {

        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();

        String location,start_time,end_time,date,event_info,creation_date;
        String Data = "";

        st.execute("select \"Date\",\"Location\",\"Start_Time\",\"End_Time\",\"Event_Info\",\"creation_date\" from \"EVENTS\" where \"MAN_ID\" = '"+man_id+"' and \"status\" = 'on' ORDER BY \"creation_date\" ASC, \"Date\" ASC");

        ResultSet rs = st.getResultSet();
        while (rs.next()) {
            date = rs.getString(1);
            location = rs.getString(2);
            start_time= rs.getString(3);
            end_time = rs.getString(4);
            event_info = rs.getString(5);
            creation_date = rs.getString(6);

            Data +=  date+"#"+location+"#"+start_time+"#"+end_time+"#"+event_info+"#"+creation_date+"#";
        }

        st.close();
        conn.close();

        return Data;
    }
    public static String getBloodCountData(String bb_id) throws SQLException, ClassNotFoundException {

        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();

        String aPlus,aMinus,bPlus,bMinus,oPlus,oMinus,abPlus,abMinus;
        String Data = "";

        st.execute("select c.\"A+\",c.\"A-\",c.\"B+\",c.\"B-\",c.\"AB+\" ,c.\"AB-\",c.\"O+\",c.\"O-\" from \"BB_BLOOD_INFO\" c where c.BB_ID = '"+bb_id+"'");

        ResultSet rs = st.getResultSet();
        while (rs.next()) {
            aPlus = rs.getString(1);
            aMinus = rs.getString(2);
            bPlus = rs.getString(3);
            bMinus = rs.getString(4);
            abPlus = rs.getString(5);
            abMinus = rs.getString(6);
            oPlus = rs.getString(7);
            oMinus = rs.getString(8);

            Data +=  aPlus+"#"+aMinus+"#"+bPlus+"#"+bMinus+"#"+oPlus+"#"+oMinus+"#"+abPlus+"#"+abMinus;
        }

        System.out.println(Data);
        st.close();
        conn.close();

        return Data;
    }
    public static String getUser(String user_id) throws SQLException, ClassNotFoundException {

        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();

        String qry1 = "select * from \"USERS\" where \"USER_ID\" ='" + user_id + "'";
        st.execute(qry1);

        int uid = 0;
        String userData  = "";

        ResultSet rs = st.getResultSet();
        while (rs.next()) {
            uid = rs.getInt(1);

            if (uid == 0){
                userData +=  "false#";
            }else{
                userData +="true#";
                userData += uid +"#"+ rs.getString(2)+" "+rs.getString(3)+"#"+rs.getString(4)+"#"+rs.getString(5)+"#"+rs.getString(6)+"#"+ rs.getString(7);//user_id,name,phone,email,gender,bloodGroup
            }
        }


        st.close();
        conn.close();

        return userData;
    }

    public static void updateBloodCountData(String bb_id, String aPlus, String aMinus, String bPlus, String bMinus, String oPlus, String oMinus, String abPlus, String abMinus) throws SQLException, ClassNotFoundException {

        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();

        String qry1 = "UPDATE \"BB_BLOOD_INFO\" SET "+
                    "\"A+\" = "+ aPlus+","+
                    "\"A-\" = "+ aMinus+","+
                    "\"B+\" = "+ bPlus+","+
                    "\"B-\" = "+ bMinus+","+
                    "\"O+\" = "+ oPlus+","+
                    "\"O-\" = "+ oMinus+","+
                    "\"AB+\" = "+ abPlus+","+
                    "\"AB-\" = "+ abMinus+ " where BB_ID = "+bb_id;
        st.execute(qry1);

        st.close();
        conn.close();
    }


    public static void updateBloodPost(String post_id, String blood, String amount, String location, String date, String time, String phone, String patient_info) throws SQLException, ClassNotFoundException {

        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();

        String qry1 = "UPDATE BLOOD_POSTS SET "+
                "\"bloodGroup\" = '"+ blood+"',"+
                "\"amount\" = '"+ amount+"',"+
                "\"location\" = '"+ location+"',"+
                "\"lastDate\" = TO_DATE('"+ date+"','yyyy-mm-dd'),"+
                "\"time\" = '"+ time+"',"+
                "\"phone\" = '"+ phone+"',"+
                "\"patientInfo\" = '"+ patient_info+ "' where POST_ID = "+ post_id;
        st.execute(qry1);

        st.close();
        conn.close();
    }

    public static void deleteYourBloodPost(String post_id) throws SQLException, ClassNotFoundException {

        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();

        String qry1 = "DELETE FROM BLOOD_POSTS WHERE POST_ID = "+post_id;
        st.execute(qry1);
        st.close();
        conn.close();
    }

    public static boolean updateBloodCountAfterApproval(String bb_id, String req_id, String blood_group, String amount, String status) throws SQLException, ClassNotFoundException {

        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();

        if(status.equals("Approved")){

            String qry1 = "Select \""+blood_group+"\" from BB_BLOOD_INFO where BB_ID = "+bb_id;
            st.execute(qry1);

            String cnt="0";

            ResultSet rs = st.getResultSet();
            while (rs.next()) {
                cnt = rs.getString(1);
            }

            int remaining = Integer.parseInt(cnt)-Integer.parseInt(amount);

            if(remaining>=0){

                String qry2 = "UPDATE \"BB_BLOOD_INFO\" SET "+
                        "\""+blood_group+"\" = "+ remaining+" where BB_ID = "+bb_id;
                st.execute(qry2);

                String qry3 = "UPDATE \"USER_BB_BLOOD_REQUESTS\" SET "+
                        "\"Status\" = 'Approved' where REQ_ID = "+req_id;
                st.execute(qry3);

                st.close();
                conn.close();
                return true;
            }
            st.close();
            conn.close();
            return false;
        }
        else{

            String qry1 = "DELETE FROM USER_BB_BLOOD_REQUESTS WHERE REQ_ID = "+req_id;
            st.execute(qry1);
            st.close();
            conn.close();

            return true;
        }
    }

    public static void refreshDatabase() throws SQLException, ClassNotFoundException {

        Connection con = ServerMain.getConnection();
        CallableStatement cs = con.prepareCall("{call refresh_blood_post_table}");
        //cs.registerOutParameter(1, Types.VARCHAR);
        cs.execute();

        cs = con.prepareCall("{call refresh_donation_post_table}");
        cs.execute();
        cs = con.prepareCall("{call refresh_event_post_table}");
        cs.execute();
        cs = con.prepareCall("{call refresh_blood_req_table}");
        cs.execute();
    }

}