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

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.squid.checks.SquidCheck;
import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.javascript.parser.EcmaScriptGrammar;
import org.sonar.sslr.parser.LexerlessGrammar;

@Rule(
  key = "S1301",
  priority = Priority.MINOR)
@BelongsToProfile(title = CheckList.SONAR_WAY_PROFILE, priority = Priority.MINOR)
public class SwitchWithNotEnoughCaseCheck extends SquidCheck<LexerlessGrammar> {

  @Override
  public void init() {
    subscribeTo(EcmaScriptGrammar.CASE_BLOCK);
  }

  @Override
  public void visitNode(AstNode astNode) {
    if (isLessThanThreeCases(astNode)) {
      getContext().createLineViolation(this, "Replace this \"switch\" statement with \"if\" statements to increase readability.", astNode);
    }
  }

  public static boolean isLessThanThreeCases(AstNode caseBlock) {
    int numberOfCase = 0;

    for (AstNode caseClauses : caseBlock.getChildren(EcmaScriptGrammar.CASE_CLAUSES)) {
      numberOfCase += caseClauses.getChildren(EcmaScriptGrammar.CASE_CLAUSE).size();
    }

    if (caseBlock.getFirstChild(EcmaScriptGrammar.DEFAULT_CLAUSE) != null) {
      numberOfCase++;
    }

    return numberOfCase < 3;
  }


}
