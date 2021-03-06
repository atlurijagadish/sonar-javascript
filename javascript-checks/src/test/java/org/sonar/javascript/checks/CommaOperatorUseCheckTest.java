/*
 * SonarQube JavaScript Plugin
 * Copyright (C) 2011 SonarSource and Eriks Nukis
 * dev@sonar.codehaus.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.javascript.checks;

import com.sonar.sslr.squid.checks.CheckMessagesVerifier;
import org.junit.Test;
import org.sonar.javascript.JavaScriptAstScanner;
import org.sonar.squid.api.SourceFile;

import java.io.File;

public class CommaOperatorUseCheckTest {

  private CommaOperatorUseCheck check = new CommaOperatorUseCheck();

  @Test
  public void test() {
    SourceFile file = JavaScriptAstScanner.scanSingleFile(new File("src/test/resources/checks/commaOperatorUse.js"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages())
      .next().atLine(1).withMessage("Remove use of this comma operator.")
      .next().atLine(3)
      .next().atLine(6)
      .next().atLine(8)
      .next().atLine(9)
      .next().atLine(13)
      .next().atLine(16)
      .next().atLine(19)
      .next().atLine(21)
      .next().atLine(22)
      .next().atLine(23)
      .next().atLine(25)
      .next().atLine(27)
      .next().atLine(29).withMessage("Remove use of all comma operators in this expression.")
      .next().atLine(33)
      .noMore();
  }
}
