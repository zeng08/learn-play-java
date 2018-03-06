package models;

import io.ebean.annotation.NotNull;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "token")
public class Token extends BaseModel {
  @NotNull
  @Column(name = "access_token", unique = true)
  public String accessToken;
  @NotNull
  @Column(name = "refresh_token", unique = true)
  public String refreshToken;
  @NotNull
  @Column(name = "expires_in")
  public long expiresIn;
  @OneToOne
  @NotNull
  public User user;

  /** 如果已经过期，那么这个Token就无效了，不能作为数据返回 */
  public boolean isExpires() {
    long lastSecond = TimeUnit.MILLISECONDS.toSeconds(
        modified.after(created) ? modified.getTime() : created.getTime());
    long nowSecond = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
    return nowSecond - lastSecond >= expiresIn;
  }

  @Override public int hashCode() {
    return Objects.hash(super.hashCode(), accessToken, refreshToken, expiresIn, user);
  }

  @Override public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (!(obj instanceof Token)) {
      return false;
    }

    Token other = (Token) obj;
    return super.equals(obj)
        && Objects.equals(accessToken, other.accessToken)
        && Objects.equals(refreshToken, other.refreshToken)
        && Objects.equals(expiresIn, other.expiresIn)
        && Objects.equals(user, other.user);
  }

  @Override public String toString() {
    return baseStringHelper()
        .add("accessToken", accessToken)
        .add("refreshToken", refreshToken)
        .add("expiresIn", expiresIn)
        .add("user", user)
        .toString();
  }
}