package pl.nw.oceniarka;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


//@SpringBootTest
class HehexdApplicationTests {

	Calculator calculator = new Calculator();


	@Test
	public void hehexd() {
		// given
		int n1 = 20;
		int n2 = 30;
		// when
		int res = calculator.add(n1, n2);
		// then
		assertThat(res).isEqualTo(50);

	}

	class Calculator{
		int add(int a, int b){
			return a + b;
		}
	}

}
