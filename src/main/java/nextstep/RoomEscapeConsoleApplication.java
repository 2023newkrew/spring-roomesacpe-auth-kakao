package nextstep;

import nextstep.reservations.domain.entity.reservation.Reservation;
import nextstep.reservations.domain.entity.theme.Theme;
import nextstep.reservations.exceptions.reservation.exception.DuplicateReservationException;
import nextstep.reservations.exceptions.reservation.exception.NoSuchReservationException;
import nextstep.reservations.exceptions.theme.exception.NoSuchThemeException;
import nextstep.reservations.repository.reservation.JdbcReservationRepository;
import nextstep.reservations.repository.reservation.ReservationRepository;
import nextstep.reservations.util.jdbc.JdbcUtil;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;


public class RoomEscapeConsoleApplication {
    private static final String ADD = "add";
    private static final String FIND = "find";
    private static final String DELETE = "delete";
    private static final String QUIT = "quit";

    public static void main(String[] args) {
        JdbcUtil.getConnection();
        Scanner scanner = new Scanner(System.in);

        ReservationRepository consoleReservationRepository = new JdbcReservationRepository();
        long reservationIdIndex = 0L;

        Theme theme = Theme.builder()
                .name("워너고홈")
                .desc("병맛 어드벤처 회사 코믹물")
                .price(29_000)
                .build();

        while (true) {
            System.out.println();
            System.out.println("### 명령어를 입력하세요. ###");
            System.out.println("- 예약하기: add {date},{time},{name} ex) add 2022-08-11,13:00,류성현");
            System.out.println("- 예약조회: find {id} ex) find 1");
            System.out.println("- 예약취소: delete {id} ex) delete 1");
            System.out.println("- 종료: quit");

            String input = scanner.nextLine();
            if (input.startsWith(ADD)) {
                String params = input.split(" ")[1];

                String date = params.split(",")[0];
                String time = params.split(",")[1];
                String name = params.split(",")[2];

                Reservation reservation = new Reservation(
                        ++reservationIdIndex,
                        LocalDate.parse(date),
                        LocalTime.parse(time + ":00"),
                        name,
                        theme
                );

                Long createdId;
                try {
                    createdId = consoleReservationRepository.add(reservation);
                }
                catch (DuplicateKeyException e) {
                    System.out.println("해당 시간에 해당 테마의 중복된 예약이 있습니다.");
                    throw new DuplicateReservationException();
                }
                catch (DataIntegrityViolationException e) {
                    System.out.println("존재하지 않는 테마입니다.");
                    throw new NoSuchThemeException();
                }

                System.out.println("예약이 등록되었습니다.");
                System.out.println("예약 번호: " + createdId);
                System.out.println("예약 날짜: " + reservation.getDate());
                System.out.println("예약 시간: " + reservation.getTime());
                System.out.println("예약자 이름: " + reservation.getName());
            }

            if (input.startsWith(FIND)) {
                String params = input.split(" ")[1];

                Long id = Long.parseLong(params.split(",")[0]);
                Reservation reservation = consoleReservationRepository.findById(id);

                System.out.println("예약 번호: " + reservation.getId());
                System.out.println("예약 날짜: " + reservation.getDate());
                System.out.println("예약 시간: " + reservation.getTime());
                System.out.println("예약자 이름: " + reservation.getName());
                System.out.println("예약 테마 이름: " + reservation.getTheme().getName());
                System.out.println("예약 테마 설명: " + reservation.getTheme().getDesc());
                System.out.println("예약 테마 가격: " + reservation.getTheme().getPrice());
            }

            if (input.startsWith(DELETE)) {
                String params = input.split(" ")[1];

                Long id = Long.parseLong(params.split(",")[0]);
                int removeCount = consoleReservationRepository.remove(id);
                if(removeCount == 0) throw new NoSuchReservationException();
                System.out.println("예약이 취소되었습니다.");

            }

            if (input.equals(QUIT)) {
                break;
            }
        }
    }
}
