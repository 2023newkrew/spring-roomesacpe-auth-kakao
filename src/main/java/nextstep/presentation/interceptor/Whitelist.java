package nextstep.presentation.interceptor;

import org.springframework.http.HttpMethod;

import java.util.List;

public class Whitelist {

    private static List<EndPoint> endPoints = List.of(
            new EndPoint(HttpMethod.POST.name(), "/members"),
            new EndPoint(HttpMethod.GET.name(), "/reservations")
    );

    public static boolean contains(String httpMethod, String requestUrl) {
        return endPoints.stream()
                .anyMatch(endPoint -> endPoint.isSame(httpMethod, requestUrl));
    }

}
