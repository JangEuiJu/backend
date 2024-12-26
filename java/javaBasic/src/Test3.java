import java.util.Scanner;

/**
 * 흐름제어
 *  - 조건문 : if ~ else if ~ else ~
 *      - 콘솔 입력 사용
 *  - 반복문 : for ~,  do ~ while, while ~ , continue, break
 *  - 게임 제작(간단하게) - 스킵
 *      - 콘솔 입력 사용
 */

public class Test3 {
    public static void main(String[] args) {
        // 조건문 연습
        flowcontrol1();
        // 반복문-for 연습 : 지정된 횟수 반복
        //flowcontrol2();
        // 반복문-while 연습 : 무한루프(종료 시점 알수 없다)
        //flowcontrol3();
    }

    private static void flowcontrol1() {
        // 1. 콘솔 입력
        Scanner sc = new Scanner(System.in); // 콘솔에서 사용자 입력 대기하는 코드
        while(true)
        {
            try {
                System.out.println("Enter number: ");
                // 2. 사용자 입력후 엔터 => 값이 반환
                int userInputValue = sc.nextInt(); // 입력 대기
                // 3. 만약 100을 입렷했다면, 종료 혹은 반복문 탈출
                if (userInputValue == 100) break;
                // 4. 조건식 사용, JS 조건문 동일, 조건이 2개? 3개?
                if (userInputValue > 10) {
                    System.out.println("10보다 초과한다");
                } else {
                    System.out.println("10보다 작거나 같다");
                }
            }catch (Exception e) {
                System.out.println("오류 : " + e.getLocalizedMessage() );
            }
        }
        // 입력 닫기
        sc.close();
    }
}
