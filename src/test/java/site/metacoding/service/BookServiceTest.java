package site.metacoding.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import site.metacoding.domain.BookRepository;
import site.metacoding.util.MailSender;
import site.metacoding.util.MailSenderStub;
import site.metacoding.web.dto.BookRespDto;
import site.metacoding.web.dto.BookSaveReqDto;


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
		assertThat(dto.getTitle()).isEqualTo(bookRespDto.getTitle());
		assertThat(dto.getAuthor()).isEqualTo(bookRespDto.getAuthor());
	}
}
