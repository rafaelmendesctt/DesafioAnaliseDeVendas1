package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import entities.Sale;

public class Program {

	public static void main(String[] args) {

		Locale.setDefault(Locale.US);
		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter full file path: ");
		String path = scanner.nextLine();

		try (BufferedReader br = new BufferedReader(new FileReader(path))) {

			List<Sale> listSale = new ArrayList<>();

			String line = br.readLine();

			while (line != null) {
				String[] fields = line.split(",");
				listSale.add(new Sale(Integer.parseInt(fields[0]), Integer.parseInt(fields[1]), fields[2],
						Integer.parseInt(fields[3]), Double.parseDouble(fields[4])));
				line = br.readLine();
			}

			Comparator<Sale> comp = (avg1, avg2) -> avg1.averagePrice().compareTo(avg2.averagePrice());

			List<Sale> firstSale = listSale.stream()
					.filter(sale -> sale.getYear() == 2016)
					.sorted(comp.reversed())
					.limit(5)
					.collect(Collectors.toList());

			firstSale.forEach(System.out::println);

			Double employeeLogan = listSale.stream()
					.filter(sale -> sale.getSeller().equals("Logan"))
					.filter(sale -> sale.getMonth() == 1 || sale.getMonth() == 7)
					.map(sale -> sale.getTotal())
					.reduce(0.0, (x, y) -> x + y);

			System.out.println();
			System.out.println("Valor total vendido pelo vendedor Logan nos meses 1 e 7 = " + String.format("%.2f", employeeLogan));

		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
		scanner.close();
	}
}