package com.shoppingcart.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Scanner;

public class NumberToWordFormat {
	private int numerator, denominator;
	private static final String[] tensNames = {
			"",
			" ten",
			" twenty",
			" thirty",
			" forty",
			" fifty",
			" sixty",
			" seventy",
			" eighty",
			" ninety"
	};

	private static final String[] numNames = {
			"",
			" one",
			" two",
			" three",
			" four",
			" five",
			" six",
			" seven",
			" eight",
			" nine",
			" ten",
			" eleven",
			" twelve",
			" thirteen",
			" fourteen",
			" fifteen",
			" sixteen",
			" seventeen",
			" eighteen",
			" nineteen"
	};

	private NumberToWordFormat() {}

	private static String convertLessThanOneThousand(int number) {
		String soFar;

		if (number % 100 < 20){
			soFar = numNames[number % 100];
			number /= 100;
		}
		else {
			soFar = numNames[number % 10];
			number /= 10;

			soFar = tensNames[number % 10] + soFar;
			number /= 10;
		}
		if (number == 0) return soFar;
		return numNames[number] + " hundred" + soFar;
	}


	public static String convert(long number) {
		if (number == 0) {
			return "zero"; 
		}
		String snumber = Long.toString(number);
		// pad with "0"
		String mask = "000000000000";
		DecimalFormat df = new DecimalFormat(mask);
		snumber = df.format(number);

		// XXXnnnnnnnnn
		int billions = Integer.parseInt(snumber.substring(0,3));
		// nnnXXXnnnnnn
		int millions  = Integer.parseInt(snumber.substring(3,6));
		// nnnnnnXXXnnn
		int hundredThousands = Integer.parseInt(snumber.substring(6,9));
		// nnnnnnnnnXXX
		int thousands = Integer.parseInt(snumber.substring(9,12));

		String tradBillions;
		switch (billions) {
		case 0:
			tradBillions = "";
			break;
		case 1 :
			tradBillions = convertLessThanOneThousand(billions)
			+ " billion ";
			break;
		default :
			tradBillions = convertLessThanOneThousand(billions)
			+ " billion ";
		}
		String result =  tradBillions;

		String tradMillions;
		switch (millions) {
		case 0:
			tradMillions = "";
			break;
		case 1 :
			tradMillions = convertLessThanOneThousand(millions)
			+ " million ";
			break;
		default :
			tradMillions = convertLessThanOneThousand(millions)
			+ " million ";
		}
		result =  result + tradMillions;

		String tradHundredThousands;
		switch (hundredThousands) {
		case 0:
			tradHundredThousands = "";
			break;
		case 1 :
			tradHundredThousands = "one thousand ";
			break;
		default :
			tradHundredThousands = convertLessThanOneThousand(hundredThousands)
			+ " thousand ";
		}
		result =  result + tradHundredThousands;

		String tradThousand;
		tradThousand = convertLessThanOneThousand(thousands);
		result =  result + tradThousand;
		// remove extra spaces!
		return result.replaceAll("^\\s+", "").replaceAll("\\b\\s{2,}\\b", " ");
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String currencyNumber = null;
		System.out.print("Please enter the number : ");
		currencyNumber = scanner.nextLine();
		//currencyNumber = "1,134.78";
		if(currencyNumber.contains(".") && currencyNumber.contains(",")) {
			String decimalSeperator = ".";
			String currenySeperator = ",";
			int dotIndex = currencyNumber.indexOf(".");
			int commaIndex = currencyNumber.indexOf(",");
			if(dotIndex < commaIndex) {
				decimalSeperator = ",";
				currenySeperator = ".";
			}
			currencyNumber = currencyNumber.replace(currenySeperator, "");
			currencyNumber = currencyNumber.replace(decimalSeperator, ".");
		} else if(currencyNumber.contains(",")) {
			currencyNumber = currencyNumber.replace(",", "");
		}
		Double doubleValue = Double.valueOf(currencyNumber);
		String fraction = null;
		if(currencyNumber.indexOf(".") > -1) {
			fraction = "0"+currencyNumber.substring(currencyNumber.indexOf("."), currencyNumber.length());
			System.out.println(fraction);
		}
		long iPart = doubleValue.longValue();
		String words = NumberToWordFormat.convert(iPart);
		String output = words.substring(0, 1).toUpperCase() + words.substring(1);
		output +=" dollars";
		if(fraction != null) {
			double fractionDouble = Double.valueOf(fraction);
			output += " and "+getFraction(fractionDouble);
		}
		System.out.println(output);
	}
	
	private static String getFraction(Double doubleValue) {
		String stringNumber = String.valueOf(doubleValue);
        int numberDigitsDecimals = stringNumber.length() - 1 - stringNumber.indexOf('.');
        long denominator = 1;
        for (int i = 0; i < numberDigitsDecimals; i++) {
        	doubleValue *= 10;
            denominator *= 10;
        }

       long numerator = (long) Math.round(doubleValue);
       long greatestCommonFactor = greatestCommonFactor(numerator, denominator);
       numerator = numerator / greatestCommonFactor;
       denominator = denominator / greatestCommonFactor;
       return String.valueOf(numerator) + "/" + String.valueOf(denominator);
	}
	
	public static long greatestCommonFactor(long num, long denom) {
        if (denom == 0) {
            return num;
        }
        return greatestCommonFactor(denom, num % denom);
    }
	
	public String toString() {
        return String.valueOf(numerator) + "/" + String.valueOf(denominator);
    }
}