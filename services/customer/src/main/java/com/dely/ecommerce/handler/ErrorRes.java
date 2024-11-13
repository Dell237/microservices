package com.dely.ecommerce.handler;

import java.util.Map;

public record ErrorRes(
        Map<String, String> errors) {

}
