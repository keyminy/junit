package site.metacoding.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

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
	}//데이터준비()의 트랜잭션은 어디까지 존재할까?
	//가정 1 : [ 데이터준비() + 1.책등록 ] (T) , [ 데이터준비() + 2.책목록보기 ] (T) -> 사이즈 1 (검증완료)
	//가정 2 : [ 데이터준비() + 1.책등록 +  데이터준비() + 2.책목록보기 ] (T) -> 사이즈 2 (검증 실패)
	//즉,데이터준비()의 트랜잭션은 다음 메서드 까지만 트랜잭션의 범위이다.
	
	//1. 책 등록
	@Test
	public void 책등록_test() {
		//given(데이터 준비)
		String title = "junit5";
		String author = "메타코딩";
		/*DTO만들지 말고, 
		 * 해당 매개변수 2개로 바로 Book Entity 만들자 */
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
	@Sql("classpath:db/tableInit.sql")
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

	//4.책 삭제
	@Sql("classpath:db/tableInit.sql")
	@Test
	public void 책삭제_test() {
		//given
		Long id = 1L;
		
		//when
		bookRepository.deleteById(id);
		
		//then
		Optional<Book> bookPS = bookRepository.findById(id);
		/*
		if(bookPS.isPresent()) {
			//Optional값이 존재할때 => 테스트 검증 실패
		}else {
			//없어야 테스트 검증 성공
		}
		*/
		
		/*assertFalse(false)처럼 매개변수 값이 false여야 테스트 성공 */
		assertFalse(bookPS.isPresent());
	}
	
	// 초기 값(@BeforeEach) : 1, junit, 겟인데어
	//5.책 수정
	@Sql("classpath:db/tableInit.sql")
	@Test
	public void 책수정_test() {
		//given
		Long id = 1L;
		String title = "junit5";
		String author = "메타코딩";
		Book book = new Book(id,title,author);
		
		//when
		bookRepository.findAll().stream()
		.forEach(b->{
			System.out.println(b.getId());
			System.out.println(b.getTitle());
			System.out.println(b.getAuthor());
			System.out.println("1.=======================");
		});		
		/* save를 통해 update하기(id:1번이 존재하므로 insert가아닌 update로 수행됨) */
		Book bookPS = bookRepository.save(book);
		/* 값 확인해보기 */
		bookRepository.findAll().stream()
					.forEach(b->{
						System.out.println(b.getId());
						System.out.println(b.getTitle());
						System.out.println(b.getAuthor());
						System.out.println("2.=======================");
					});		
		
		System.out.println(bookPS.getId());
		System.out.println(bookPS.getTitle());
		System.out.println(bookPS.getAuthor());
		System.out.println("3.$$$$$$$$$$$$$$$$$$$$$$$$");
		//then
		assertEquals(id, bookPS.getId());
		assertEquals(title, bookPS.getTitle());
		assertEquals(author, bookPS.getAuthor());
	}
	
}
