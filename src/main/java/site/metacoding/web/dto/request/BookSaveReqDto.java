package site.metacoding.web.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import site.metacoding.domain.Book;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Setter //Controller에서 Setter가 호출되면서,Dto에 값이 채워짐
@Getter
@ToString
public class BookSaveReqDto {
	@Size(min = 1,max = 50)
	@NotBlank
	private String title;
	@Size(min = 2,max = 20)
	@NotBlank
	private String author;
	
	public Book toEntity() {
		return Book.builder()
					.title(title)
					.author(author)
					.build();
	}
}
