package nextstep;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<Reservation> reservations = new ArrayList<>();

        while (true) {
            System.out.println("메뉴를 선택하세요.");
            System.out.println("1: 예약");
            System.out.println("2: 예약 취소");
            System.out.println("3: 예약 조회");
            System.out.println("4: 종료");

            String menuInput = scanner.nextLine();
            if ("1".equals(menuInput)) {

                System.out.println("예약 정보를 입력하세요.");
                System.out.println();

                System.out.println("날짜 (ex.2022-08-11)");
                String date = scanner.nextLine();

                System.out.println("시간 (ex.13:00)");
                String time = scanner.nextLine();

                System.out.println("예약자 이름");
                String name = scanner.nextLine();

                Reservation reservation = new Reservation(
                        LocalDate.parse(date),
                        LocalTime.parse(time + ":00"),
                        name
                );

                reservations.add(reservation);
                System.out.println("예약이 등록되었습니다.");
            }

            if ("2".equals(menuInput)) {
                System.out.println("취소할 예약 정보를 입력하세요.");
                System.out.println();

                System.out.println("날짜 (ex.2022-08-11)");
                String date = scanner.nextLine();

                System.out.println("시간 (ex.13:00)");
                String time = scanner.nextLine();

                reservations.stream()
                        .filter(it -> Objects.equals(it.getDate(), LocalDate.parse(date)) && Objects.equals(it.getTime(), LocalDate.parse(time)))
                        .findFirst()
                        .ifPresent(reservations::remove);

                System.out.println("예약이 취소되었습니다.");
            }

            if ("3".equals(menuInput)) {
                System.out.println("예약 조회 할 날짜를 입력하세요.");
                System.out.println();

                System.out.println("날짜 (ex.2022-08-11)");
                String date = scanner.nextLine();

                reservations.stream()
                        .filter(it -> it.getDate().isEqual(LocalDate.parse(date)))
                        .collect(Collectors.toList())
                        .forEach(System.out::println);
            }

            if ("4".equals(menuInput)) {
                break;
            }
        }
    }
}