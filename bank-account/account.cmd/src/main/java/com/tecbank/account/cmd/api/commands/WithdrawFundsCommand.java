/*
 * Copyright (c) 2023. Paul Nwabudike
 * Since: February 2023
 * Author: Paul Nwabudike
 * Name: WithdrawFundsCommand.java
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at   http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 *
 */

package com.tecbank.account.cmd.api.commands;

import com.tecbank.cqrs.core.commands.BaseCommand;
import lombok.Data;

@Data

public class WithdrawFundsCommand extends BaseCommand {
    private double amount;

}
