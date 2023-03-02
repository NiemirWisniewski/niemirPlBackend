package pl.nw.oceniarka.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ErrorMessageResponse {

    private final String message;

}
