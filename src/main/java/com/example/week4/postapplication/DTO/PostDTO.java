package com.example.week4.postapplication.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {

    private Long postId;

    @NotBlank
    @Size(min=2,max = 40, message="Title should have 2-40 characters")
    private String title;

    @NotBlank
    private String description;

    @JsonFormat(pattern = "dd-MMM-yyyy HH:mm:ss")
    private LocalDateTime createdTime;
}
