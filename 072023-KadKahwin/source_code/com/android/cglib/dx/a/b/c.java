package com.android.cglib.dx.a.b;

import com.android.cglib.dx.d.a;
import com.android.cglib.dx.d.i;

public final class c extends ag {
  private b a;
  
  public y a() {
    return y.v;
  }
  
  public void a(l paraml) {
    this.a = paraml.d().<b>b(this.a);
  }
  
  protected void a_(l paraml, a parama) {
    int i = this.a.e();
    if (parama.a()) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("  annotations_off: ");
      stringBuilder.append(i.a(i));
      parama.a(4, stringBuilder.toString());
    } 
    parama.d(i);
  }
  
  public String b() {
    return this.a.b();
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\android\cglib\dx\a\b\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */