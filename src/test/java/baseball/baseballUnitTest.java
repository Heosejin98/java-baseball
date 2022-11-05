package baseball;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class baseballUnitTest {

    @Nested
    @DisplayName("유저 입력값 검증")
    class InputTestCase {

        @Test
        @DisplayName("유저 입력값 숫자 2개")
        void 적은입력() {
            assertThatThrownBy(() -> new InputException("12"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("숫자를 3개 입력하세요");
        }

        @Test
        @DisplayName("유저 입력값 숫자 4개")
        void 많은입력() {
            assertThatThrownBy(() -> new InputException("1234"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("숫자를 3개 입력하세요");
        }

        @Test
        @DisplayName("유저 입력값 형식 오류")
        void 문자입력() {
            assertThatThrownBy(() -> new InputException("12a"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("숫자만 입력하세요");
        }

        @Test
        @DisplayName("중복 숫자 입력")
        void 중복숫자() {
            assertThatThrownBy(() -> new InputException("121"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("중복된 숫자가 있습니다");
        }

        @Test
        @DisplayName("음수 입력")
        void 음수입력() {
            assertThatThrownBy(() -> new InputException("-12"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("양수만 입력하세요");
        }
    }

    @Nested
    @DisplayName("게임 기능 검증")
    class baseballGameTestCase {

        BaseballGame baseballGame = new BaseballGame();

        @Test
        @DisplayName("스트라이크 볼 판독기")
        void 스트라이크() {
            baseballGame.ballReader(1, 1, true);
            assertThat(baseballGame.getStrike()).isEqualTo(1);
        }

        @Test
        @DisplayName("ball 볼 판독기")
        void 볼() {
            baseballGame.ballReader(1, 1, false);
            assertThat(baseballGame.getBall()).isEqualTo(1);
        }

        @Test
        @DisplayName("볼 판독기 (노스트라이크 노볼)")
        void 노볼_노스트라이크() {
            baseballGame.ballReader(1, 3, false);
            assertThat(baseballGame.getBall()).isEqualTo(0);
        }

        @Test
        @DisplayName("공던지기 3스트라이크")
        void 공던지기case1() {
            ArrayList<Integer> user = new ArrayList<>(Arrays.asList(1, 2, 3));
            ArrayList<Integer> computer = new ArrayList<>(Arrays.asList(1, 2, 3));

            baseballGame.throwball(user, computer);
            assertThat(baseballGame.getStrike()).isEqualTo(3);
        }

        @Test
        @DisplayName("공던지기 1스트라이크 1볼")
        void 공던지기case2() {
            ArrayList<Integer> user = new ArrayList<>(Arrays.asList(1, 3, 4));
            ArrayList<Integer> computer = new ArrayList<>(Arrays.asList(1, 5, 3));

            baseballGame.throwball(user, computer);
            assertThat(baseballGame.getStrike()).isEqualTo(1);
            assertThat(baseballGame.getBall()).isEqualTo(1);
        }


        @Test
        @DisplayName("print hint test (3스크라이크)")
        void 결과case1() {
            OutputStream out = new ByteArrayOutputStream();
            System.setOut(new PrintStream(out));
            ArrayList<Integer> user = new ArrayList<>(Arrays.asList(1, 5, 4));
            ArrayList<Integer> computer = new ArrayList<>(Arrays.asList(1, 5, 4));
            final String message = 3 + "스트라이크\r\n";

            baseballGame.throwball(user, computer);
            baseballGame.printHint();
            assertThat(message).isEqualTo(out.toString());
        }

    }
}