package com.example.gtc.src.comment.controller;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags ="댓글 API")
public class CommentController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

}
