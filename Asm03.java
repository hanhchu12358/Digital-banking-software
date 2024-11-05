package asm03.models;

import asm02.models.Account;
import asm02.models.Customer;

import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Asm03 {
    // Khai báo hằng số AUTHOR
    public static final String AUTHOR = "FX20430";

    // Khai báo hằng số VERSION
    public static final String VERSION = "v3.0.0";

    public static final int EXIT_COMMAND_CODE = 0;
    public static final int EXIT_ERROR_CODE = -1;

    public static final Scanner scanner = new Scanner(System.in);
    public static final DigitalBank activeBank = new DigitalBank();
    public static final String CUSTOMER_ID = "027197010353";
    public static String CUSTOMER_NAME = "Hanh";

    public static void main(String[] args) {
        try {
            System.out.println(getDivider());
            // Hiển thị mô tả chương trình
            System.out.printf("| NGAN HANG SO | %s@%s                 |\n", AUTHOR, VERSION);
            System.out.println("+----------+-------------------------+----------+");
            // Hiển thị menu chức năng
            System.out.println(" 1. Thong tin khach hang");
            System.out.println(" 2. Them tai khoan ATM");
            System.out.println(" 3. Them tai khoan tin dung");
            System.out.println(" 4. Rut tien");
            System.out.println(" 5. Lich su giao dich");
            System.out.println(" 0. Thoat");
            System.out.println(getDivider());

            activeBank.addCustomer(CUSTOMER_ID, CUSTOMER_NAME);

            int choice = 1;
            do {
                try {
                    //  Nhập lựa chọn từ người dùng
                    System.out.print("Chuc nang: ");
                    choice = Integer.parseInt(scanner.nextLine());
                    System.out.println(getDivider());

                    switch (choice) {
                        case 0:
                            System.out.println("Thoat chuong trinh.");
                            System.exit(EXIT_COMMAND_CODE);
                            break;
                        case 1:
                            showCustomer();
                            break;
                        case 2:
                            addAccount("Savings");
                            break;
                        case 3:
                            addAccount("Loan");
                            break;
                        case 4:
                            withdraw();
                            break;
                        case 5:
                            transaction();
                            break;
                        default:
                            System.out.println("Lua chon khong hop le. Vui long chon lai.");
                    }
                }
                // Xử lý lỗi nhập liệu khi người dùng nhập chuỗi thay vì số
                catch (NumberFormatException e) {
                    System.out.println("Lua chon khong hop le. Vui long nhap lai.");
                }
            } while (choice != 0);
        } catch (Exception e) {
            System.err.println("Loi he thong nghiem trong:");
            System.err.println(e.getMessage());
            e.printStackTrace();
            System.exit(EXIT_ERROR_CODE);
        }
    }

    // In thông tin của khách hàng
    public static void showCustomer() {
        Customer customer = activeBank.getCustomerById(CUSTOMER_ID);
        if (customer != null)
            customer.displayInformation();
    }

    // Thêm tài khoản cho khách hàng
    public static void addAccount(String accountType) {
        System.out.print("Nhap ma STK gom 6 chu so: ");
        String accountNumber = scanner.nextLine();
        while (validateAccount(accountNumber) || isAccountExisted(accountNumber) || activeBank.hasDuplicateAccountNumber(accountNumber)) {
            System.out.print("STK khong hop le hoac da ton tai. Vui long nhap lai: ");
            accountNumber = scanner.nextLine();
        }

        // Nhập số dư tài khoản
        double initialBalance = 0;
        boolean invalidInput;
        do {
            try {
                System.out.print("Nhap so du: ");
                initialBalance = scanner.nextDouble();
                scanner.nextLine();

                if (initialBalance < 50000) {
                    System.out.println("So du tai khoan khong hop le. Vui long nhap lai.");
                    invalidInput = true;
                } else {
                    invalidInput = false;
                }
            }
            // Xử lý lỗi nhập liệu khi người dùng nhập chuỗi thay vì số
            catch (InputMismatchException e) {
                System.out.println("Lua chon khong hop le. Vui long nhap lai.");
                scanner.nextLine(); // Đọc dòng mới để xóa bộ đệm đầu vào
                invalidInput = true;
            }
        } while (invalidInput);

        // Tạo và thêm tài khoản vào activeBank dựa vào loại tài khoản
        if ("Savings".equalsIgnoreCase(accountType)) {
            SavingsAccount savingsAccount = new SavingsAccount(accountNumber, initialBalance);
            savingsAccount.setAccountType(AccountType.SAVINGS);
            Transaction initialTransaction = new Transaction(accountNumber, initialBalance, true);
            initialTransaction.setStatus(true);
            savingsAccount.addTransaction(initialTransaction);
            activeBank.addAccount(CUSTOMER_ID, savingsAccount);
            System.out.println("Them tai khoan ATM: " + accountNumber);
        } else if ("Loan".equalsIgnoreCase(accountType)) {
            LoanAccount loanAccount = new LoanAccount(accountNumber, initialBalance);
            loanAccount.setAccountType(AccountType.LOAN);
            Transaction initialTransaction = new Transaction(accountNumber, initialBalance, true);
            initialTransaction.setStatus(true);
            loanAccount.addTransaction(initialTransaction);
            activeBank.addAccount(CUSTOMER_ID, loanAccount);
            System.out.println("Them tai khoan tin dung: " + accountNumber);
        } else {
            System.out.println("Loai tai khoan khong hop le.");
        }
    }

    // Thực hiện rút tiền
    public static void withdraw() {
        // Chọn tài khoản
        System.out.print("Nhap so tai khoan can rut tien: ");
        String accountNumber = scanner.nextLine();

        double amount;
        boolean invalidInput;
        do {
            try {
                System.out.print("Nhap so tien can rut: ");
                amount = scanner.nextDouble();
                scanner.nextLine();

                if (amount < 50_000 || amount % 10_000 != 0) {
                    System.out.println("So tien rut khong hop le. Vui long nhap lai.");
                    invalidInput = true;
                } else {
                    invalidInput = false;
                }
            }
            // Xử lý lỗi nhập liệu khi người dùng nhập chuỗi thay vì số
            catch (InputMismatchException e) {
                System.out.println("Lua chon khong hop le. Vui long nhap lai.");
                scanner.nextLine(); // Đọc dòng mới để xóa bộ đệm đầu vào
                invalidInput = true;
                amount = 0;
            }
        } while (invalidInput);

        boolean success = activeBank.withdraw(CUSTOMER_ID, accountNumber, amount);

        if (success) {
            System.out.println("Rut tien thanh cong.");
        } else {
            System.out.println("Rut tien that bai.");
        }
    }

    // In ra lịch sử giao dịch
    public static void transaction() {
        Customer customer = activeBank.getCustomerById(CUSTOMER_ID);

        // Hiển thị thông tin khách hàng
        showCustomer();

        // Hiển thị danh sách tài khoản
        List<Account> accounts = customer.getAccounts();
        System.out.printf("%11s | %15s | %19s | %20s\n", "Account", "Amount", "Time", "Transaction ID");
        for (Account account : accounts) {
            List<Transaction> transactions = account.getTransactions();

            // Sắp xếp giao dịch theo thời gian
            transactions.sort(Comparator.comparing(Transaction::getTime));

            // Tìm giao dịch ban đầu
            transactions.stream()
                    .filter(Transaction::isInitial)
                    .findFirst().ifPresent(initialTrans -> System.out.printf("%s %s | %,14.2fđ | %s | %s \n",
                            "[GD]",
                            account.getAccountNumber(),
                            initialTrans.getAmount(),
                            initialTrans.getTime(),
                            initialTrans.getId()));

            // Hiển thị các giao dịch rút tiền
            for (Transaction trans : transactions) {
                if (!trans.isInitial()) {
                    double amount = -trans.getAmount();
                    if (account instanceof LoanAccount) {
                        amount -= ((LoanAccount) account).getLastTransactionFee();
                    }
                    System.out.printf("%s %s | %,14.2fđ | %s | %s\n",
                            "[GD]",
                            trans.getAccountNumber(),
                            amount,
                            trans.getTime(),
                            trans.getId());
                }
            }
        }
    }

    // Kiểm tra STK tồn tại hay chưa
    public static boolean isAccountExisted(String accountNumber) {
        if (SavingsAccount.getAccount() != null) {
            for (SavingsAccount account : SavingsAccount.getAccount()) {
                if (account.getAccountNumber().equals(accountNumber)) {
                    return true;
                }
            }
        }
        if (LoanAccount.getAccount() != null) {
            for (LoanAccount account : LoanAccount.getAccount()) {
                if (account.getAccountNumber().equals(accountNumber)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Kiểm tra STK nhập vào có hợp lệ không
    public static boolean validateAccount(String accountNumber) {
        // Kiểm tra độ dài chuỗi
        if (accountNumber.length() != 6) {
            return true;
        }

        // Kiểm tra kí tự trong chuỗi
        for (int i = 0; i < accountNumber.length(); i++) {
            char c = accountNumber.charAt(i);
            if (c < '0' || c > '9') {
                return true;
            }
        }
        return false;
    }

    public static String getDivider() {
        return "+----------+-------------------------+----------+";
    }
}
