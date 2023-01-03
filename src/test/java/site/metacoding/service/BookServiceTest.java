package site.metacoding.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import site.metacoding.domain.Book;
import site.metacoding.domain.BookRepository;
import site.metacoding.util.MailSender;
import site.metacoding.web.dto.response.BookListRespDto;
import site.metacoding.web.dto.response.BookRespDto;
import site.metacoding.web.dto.request.BookSaveReqDto;

import java.util.*;


@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

	@InjectMocks
	private BookService bookService;
	@Mock
	private BookRepository bookRepository;
	//부모 타입으로 적음 -> MailAdapter로 변경해도 괜찮아짐
	@Mock
	private MailSender mailSender;
	
	//문제점 : 서비스만 테스트하고 싶은데, Repository도 함께 테스트가 된다..
	@Test
	public void 책등록하기_테스트() {
		/* given */
		BookSaveReqDto dto = new BookSaveReqDto();
		dto.setTitle("junit강의");
		dto.setAuthor("메타코딩");

		/* stub : 행동정의,가설 */
		when(bookRepository.save(any()))
				.thenReturn(dto.toEntity());
		when(mailSender.send())
				.thenReturn(true);

		/* when */
		BookRespDto bookRespDto =  bookService.책등록하기(dto);

		/* then */
		/* 기존 방법 */
		//assertEquals(dto.getTitle(),bookRespDto.getTitle());
		//assertEquals(dto.getAuthor(),bookRespDto.getAuthor());

		/* assertj 이용 */
		//assertThat(실제로 나올 코드,actual).isEqualTo(기대값,내가 넣은 코드,expected)
		assertThat(bookRespDto.getTitle()).isEqualTo(dto.getTitle());
		assertThat(bookRespDto.getAuthor()).isEqualTo(dto.getAuthor());
	}

	//체크포인트
	@Test
	public void 책목록보기_테스트(){
		//given(파라미터로 들어올 데이터)(클라이언트에게 받을 것이 없어서 생략)

		//stub(가설)
		List<Book> books = new ArrayList<>();
		books.add(new Book(1L,"junit강의","메타코딩"));
		books.add(new Book(2L,"spring강의","겟인데어"));
		when(bookRepository.findAll()).thenReturn(books);

		//when(실제로 실행하는 부분)
		BookListRespDto bookListRespDto = bookService.책목록보기();
		//then(검증)
		assertThat(bookListRespDto.getItems().get(0).getTitle()).isEqualTo("junit강의");
		assertThat(bookListRespDto.getItems().get(0).getAuthor()).isEqualTo("메타코딩");
		assertThat(bookListRespDto.getItems().get(1).getTitle()).isEqualTo("spring강의");
		assertThat(bookListRespDto.getItems().get(1).getAuthor()).isEqualTo("겟인데어");
	}

	@Test
	public void 책한건보기_테스트(){
		//given
		Long id = 1L;

		//stub
		Book book = new Book(1L,"junit강의","메타코딩");
		Optional<Book> bookOP = Optional.of(book);
		when(bookRepository.findById(id)).thenReturn(bookOP);

		//when
		BookRespDto bookRespDto = bookService.책한건보기(id);

		//then
		assertThat(bookRespDto.getTitle()).isEqualTo(book.getTitle());
		assertThat(bookRespDto.getAuthor()).isEqualTo(book.getAuthor());
	}

	@Test
	public void 책수정하기_테스트(){
		//given
		Long id = 1L;
		BookSaveReqDto dto = new BookSaveReqDto();
		dto.setTitle("spring강의"); //"spring강의"로 수정
		dto.setAuthor("겟인데어"); //"겟인데어"로 수정

		//stub (Optional<Book> bookOP = bookRepository.findById(id);)부분
		//위에 했던거 그대로 쓰자..
		Book book = new Book(1L,"junit강의","메타코딩");//DB에 기존에 있는거
		Optional<Book> bookOP = Optional.of(book);
		when(bookRepository.findById(id)).thenReturn(bookOP);

		//when
		BookRespDto bookRespDto =  bookService.책수정하기(id,dto);

		//then
		assertThat(bookRespDto.getTitle()).isEqualTo(dto.getTitle());
		assertThat(bookRespDto.getAuthor()).isEqualTo(dto.getAuthor());
	}
}
