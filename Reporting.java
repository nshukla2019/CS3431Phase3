import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

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
                ResultSet rset = stmt.executeQuery(str);

                int admissionDate = -1;
                int totalPayment = -1;
                int patient_SSN = -1;


                // Rooms:
                // RoomNum:     FromDate:     ToDate:
                // RoomNum:     FromDate:     ToDate:
                // RoomNum:     FromDate:     ToDate:

                while (rset.next()) {
                    admissionDate = rset.getInt("admissionDate");
                    totalPayment = rset.getInt("totalPayment");
                    patient_SSN = rset.getInt("patient_SSN");
                }
                rset.close();

                ResultSet rset2 = stmt.executeQuery(str2);

                int rmNum = -1;
                Date startDate = null;
                Date endDate = null;

                while (rset2.next()) {
                    rmNum = rset2.getInt("gender");
                    startDate = rset2.getDate("startDate");
                    endDate = rset2.getDate("endDate");
                }
                rset2.close();
            }

            // TODO program needs to execute a query here over the admission table and print the following:
                // TODO the Doctor IDS printed need to be unique (no repeats)

                // Admission Number:
                // Patient SSN:
                // Admission Date (start date):
                // Total Payment:

                // Rooms:
                    // RoomNum:     FromDate:     ToDate:
                    // RoomNum:     FromDate:     ToDate:
                    // RoomNum:     FromDate:     ToDate:

                // Doctors examined the patient in this admission:
                    // Doctor ID:
                    // Doctor ID:
                    // Doctor ID:

            reader.close();
            System.exit(0);
        }
        else if (args[2].equals("4")) {
            System.out.println("Enter Admission Number:");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String admissionNumber = reader.readLine();
            System.out.println("You entered: " + admissionNumber);

            System.out.println();

            System.out.println("Enter the new total payment:");
            String newTotalPayment = reader.readLine();
            System.out.println("You entered: " + newTotalPayment);

            // TODO program needs to execute a query that updates the total payment for the given admissions number
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
