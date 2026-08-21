package article_22_static_methods;

public interface JobService {
  static String getCompanyName(){
    return "Company Name";
  }
  static String findJobByTitle(String title) {
    return title;
  }
}
