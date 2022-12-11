import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;

public class Reporting {

    public static void main(String[] args) throws IOException, SQLException {
        if (args.length < 2) {
            System.out.println("Please enter username and password when running this program.");
            System.exit(0);
        }
        else if (args.length == 2) {
            System.out.println();
            System.out.println("1 - Report Patients Basic Information");
            System.out.println("2 - Report Doctors Basic Information");
            System.out.println("3 - Report Admissions Information");
            System.out.println("4 - Update Admissions Payment");
            System.out.println();
            System.exit(0);
        }
        else if (args[2].equals("1")) {
            // prompt user
            System.out.print("Enter Patient SSN: ");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String SSN = reader.readLine();

            // connection to database
            Connection connection = returnConnection();

            if (connection != null) {
                Statement stmt = connection.createStatement();
                String str = "SELECT firstName, lastName, address FROM Patient WHERE SSN = " + SSN;
                ResultSet rset = stmt.executeQuery(str);

                String firstName = "";
                String lastName = "";
                String address = "";

                while(rset.next()) {
                    firstName = rset.getString("firstName");
                    lastName = rset.getString("lastName");
                    address = rset.getString("address");
                }

                System.out.println();
                System.out.println("Patient SSN: " + SSN);
                System.out.println("Patient First Name: " + firstName);
                System.out.println("Patient Last Name: " + lastName);
                System.out.println("Patient Address: " + address);

                rset.close();
                stmt.close();
                connection.close();
            }

            reader.close();
            System.exit(0);
        }
        else if (args[2].equals("2")) {
            // prompt user
            System.out.print("Enter Doctor ID: ");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String doctorID = reader.readLine();

            // connection to database
            Connection connection = returnConnection();

            if (connection != null) {
                Statement stmt = connection.createStatement();
                String str = "SELECT fName, lName FROM Employee WHERE ID = " + doctorID;
                String str2 = "SELECT gender, specialty, graduatedFrom FROM Doctor WHERE empID = " + doctorID;
                ResultSet rset = stmt.executeQuery(str);

                String firstName = "";
                String lastName = "";
                String gender = "";
                String gradFrom = "";
                String speciality = "";

                while(rset.next()) {
                    firstName = rset.getString("fName");
                    lastName = rset.getString("lName");
                }
                rset.close();

                ResultSet rset2 = stmt.executeQuery(str2);

                while(rset2.next()) {
                    gender = rset2.getString("gender");
                    speciality = rset2.getString("specialty");
                    gradFrom = rset2.getString("graduatedFrom");
                }
                rset2.close();

                System.out.println();
                System.out.println("Doctor ID: " + doctorID);
                System.out.println("Doctor First Name: " + firstName);
                System.out.println("Doctor Last Name: " + lastName);
                System.out.println("Doctor Gender: " + gender);
                System.out.println("Doctor Graduated From: " + gradFrom);
                System.out.println("Doctor Specialty: " + speciality);

                stmt.close();
                connection.close();
            }

            reader.close();
            System.exit(0);
        }
        else if (args[2].equals("3")) {
            System.out.print("Enter Admission Number: ");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String admissionNumber = reader.readLine();

            // connection to database
            Connection connection = returnConnection();

            if (connection != null) {
                Statement stmt = connection.createStatement();
                String str = "SELECT admissionDate, totalPayment, patient_SSN FROM Admission WHERE num = " + admissionNumber;
                String str2 = "SELECT rmNum, startDate, endDate FROM StayIn WHERE adNum = " + admissionNumber;
                String str3 = "SELECT DISTINCT drID FROM Examine WHERE adNum = " + admissionNumber;

                ResultSet rset = stmt.executeQuery(str);

                String admissionDate = "";
                String totalPayment = "";
                String patient_SSN = "";

                while (rset.next()) {
                    admissionDate = rset.getString("admissionDate");
                    totalPayment = rset.getString("totalPayment");
                    patient_SSN = rset.getString("patient_SSN");
                }
                rset.close();

                System.out.println();
                System.out.println("Admission Number: " + admissionNumber);
                System.out.println("Patient SSN: " + patient_SSN);
                System.out.println("Admission Date (start date): " + admissionDate);
                System.out.println("Total Payment: " + totalPayment);


                ResultSet rset2 = stmt.executeQuery(str2);

                ArrayList<Integer> rmNums = new ArrayList<>();
                ArrayList<Date> startDates = new ArrayList<>();
                ArrayList<Date> endDates = new ArrayList<>();

                while (rset2.next()) {
                    rmNums.add(rset2.getInt("rmNum"));
                    startDates.add(rset2.getDate("startDate"));
                    endDates.add(rset2.getDate("endDate"));
                }
                rset2.close();

                System.out.println("Rooms: ");

                for (int i = 0; i < rmNums.size(); i++) {
                    System.out.println("     Room: " + rmNums.get(i) + "     " + "From Date: " + startDates.get(i) + "     " + "End Date: " + endDates.get(i));
                }

                ResultSet rset3 = stmt.executeQuery(str3);
                ArrayList<Integer> drIDs = new ArrayList<>();

                while (rset3.next()) {
                    drIDs.add(rset3.getInt("drID"));
                }
                rset3.close();

                System.out.println("Doctors examined the patient in this admission: ");

                for (Integer drID : drIDs) {
                    System.out.println("     Doctor: " + drID);
                }
            }

            reader.close();
            System.exit(0);
        }
        else if (args[2].equals("4")) {
            System.out.print("Enter Admission Number: ");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String admissionNumber = reader.readLine();

            System.out.println();

            System.out.print("Enter the new total payment: ");
            String newTotalPayment = reader.readLine();

            // connection to database
            Connection connection = returnConnection();

            if (connection != null) {
                Statement stmt = connection.createStatement();

                String str = "UPDATE Admission SET totalPayment = " + newTotalPayment + " WHERE num = " + admissionNumber;
                int rset = stmt.executeUpdate(str);

                if (rset == 1) {
                    System.out.println();
                    System.out.println("Successfully updated!");
                }
            }

            // TODO good to check this with option 3 (execute with this admissions num to see if total payment has been updated)

            reader.close();
            System.exit(0);
        }
    }

    public static Connection returnConnection() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        }
        catch (ClassNotFoundException e) {
            System.out.println("Cannot find Oracle JDBC");
            e.printStackTrace();
        }

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@oracle.wpi.edu:1521:orcl",
                    "nshukla",
                    "NSHUKLA");
            return connection;
        }
        catch (SQLException e) {
            System.out.println("Connection failed");
            return connection;
        }
    }

}
