public class ReverseCustomerName {

    public static String reverseCustomerName(String customerName) {
        if (customerName == null) {
            return null;
        }
        char[] chars = customerName.toCharArray();
        int left = 0;
        int right = chars.length - 1;
        while (left < right) {
            char temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;
            left++;
            right--;
        }
        return new String(chars);
    }

    public static void main(String[] args) {
        System.out.println("=== Day 1 Problem 5: Reverse Customer Name ===");
        String customerName = "Sunil";
        String reversed = reverseCustomerName(customerName);
        System.out.println("Original Name: " + customerName);
        System.out.println("Reversed Name: " + reversed);
    }
}
