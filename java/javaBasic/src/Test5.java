import java.util.ArrayList;
import java.util.List;

/**
 * 컬렉션(*) API
 *      - 데이터베이스 연동 후 가져오는 데이터는 => 컬렉션 으로 처리됨!!
 *      - 맴버를 언제든지 추가,삭제 가능함
 *      - List
 *          - 순서가 있다
 *          - (*)ArrayList, LinkedList, ...
 *      - Set
 *          - 중복 데이터가 없다(중복제거)
 *          - HashSet, TreeSet
 *      - Map
 *          - 키와 값 형태로 보관 -> JSON, JS 객체 , python dict
 *          - HashMap, TreeMap
 */

public class Test5 {
    public static void main(String[] args) {
        // 1. (중요)List, 순서(*) 존재 -> 인덱스(0, 1, ..) 존재!!, 중복 가능함
        {
            // 저장 공간 준비
            // <유형> =>  제네릭 표현 (범용성 등 목적)
            // ArrayList : 자식, List: 부모
            // 크기 지정 한적 없다!!
            // 제네릭, 다형성, 컬렉션 적용
            // 제네릭을 통해서 컬렉션에서 담을 데이터의 타입 제약 -> 문자열만 받을수 있다
            // 다형성을 통해서 부모 List가 자식객체  ArrayList를 받을 수 있게 정의
            List<String> temp = new ArrayList<>();    // new 클레스명() => 객체 생성
            System.out.println(temp); // []

            // 더미데이터 사용 <- 가변크기, 언제든지 맴버를 추가할 수 있다!!
            temp.add("a");
            temp.add("b");
            temp.add("c");

            System.out.println(temp); // [a, b, c]
        }
    }
}
