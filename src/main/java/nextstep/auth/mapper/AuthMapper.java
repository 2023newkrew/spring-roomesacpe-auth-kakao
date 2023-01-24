package nextstep.auth.mapper;

import nextstep.auth.domain.AccessToken;
import nextstep.auth.dto.AccessTokenResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthMapper {

    AuthMapper INSTANCE = Mappers.getMapper(AuthMapper.class);

    default AccessTokenResponse accessTokenDomainToDto(AccessToken accessToken) {
        if (accessToken == null) {
            return null;
        }

        String token = accessToken.getAccessToken();

        return new AccessTokenResponse(token);
    }
}
