package site.metacoding.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
//DB와 관련된 컴포넌트만 메모리에 로딩됨
public class BookRepositoryTest {
	
	@Autowired //DI
	private BookRepository bookRepository;
	
	//더미데이터 만들기
	@BeforeEach
	public void 데이터준비() {
		//given(데이터 준비)
		String title = "junit";
		String author = "겟인데어";

		Book book = Book.builder()
						.title(title)
						.author(author)
						.build();
		
		bookRepository.save(book);
	}
	
	
	//1. 책 등록
	@Test
	public void 책등록_test() {
		//given(데이터 준비)
		String title = "junit5";
		String author = "메타코딩";
		/*DTO만들지 말고, 
		 * 해당 매개변수 2개로 바로 Entity 만들자 */
		Book book = Book.builder()
						.title(title)
						.author(author)
						.build();
		//when(테스트 실행)
		Book bookPS = bookRepository.save(book);
		//book PerSistence(영구적으로 저장되었다는 뜻)
		
		//then(검증)
		assertEquals(title,bookPS.getTitle());
		assertEquals(author,bookPS.getAuthor());
	} //트랜잭션이 종료되며, 저장된 데이터를 초기화시킴.
	
	//2. 책 목록 보기
	@Test
	public void 책목록보기_test() {
		//given
		String title = "junit";
		String author = "겟인데어";
		
		//when
		List<Book> booksPS = bookRepository.findAll();
		
		//then
		System.out.println("사이즈 : ================== " + booksPS.size()); //1이나옴
		assertEquals(title, booksPS.get(0).getTitle());
		assertEquals(author, booksPS.get(0).getAuthor());
	}
	//3. 책 한건 보기
	@Test
	public void 책한건보기_test() {
		//given(데이터 준비)
		String title = "junit";
		String author = "겟인데어";

		//when
		Book bookPS = bookRepository.findById(1L).get();
		
		//then
		assertEquals(title, bookPS.getTitle());
		assertEquals(author, bookPS.getAuthor());
	}
	
	//4.책 수정
	
	
	//5.책 삭제
}
