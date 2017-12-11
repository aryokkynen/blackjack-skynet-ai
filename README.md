# Generic Qlearning AI bot for blackjack and stockmarket

Project is loosely based on XinHuang123's blackjack AI [XinHuang123 Github](https://github.com/XinHuang123/BlackJack-with-Artificial-Intelligence)

# License

Copyright 2017 Leif Salminen / Eero Salomies / Antti Ryökkynen / Mokhtar Paukkio / Niko Pieviläinen

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

# Purpose of this project

AI Technology for this project is Reinforcement Learning/Qlearning.  
First versions of project was concentrated making AI to play blackjack. We achieved around 34-35% win rate for blackjack.  
After blackjack, project took new turn and we are now trying to use AI to predict/see if it can make intelligent decisions for stock market.

# Usage

First you need some stock market data, suggested to use Yahoo finance for csv download.  
Data must be in following format
* Header row: date,close,adjclose

* Date = {string}
* close = {double} | Use format 0.0
* Adjusted closing price = {double} |Stock adjusted closing price, if using data from longer perioid automatically calculates possible splits and dividends in price. Yahoo Finance offer this information | Use format 0.0

Example  
date,close,adjclose  
2000-01-03,7.23256254196167,4.525106430053711  

or look at example.csv for proper data format.

You can train AI with multiple different stocks, we are suggesting you use different stocks to train and for 'real' simulation stock you want to get results in.

You can change from code what value AI uses (current closing price or adjusted closing price).

Bot creation: SkynetStockAgent hal = new SkynetStockAgent(epsilon, discount, alpha, "HAL 9000", agent_money);
* Epsilon = Greedy Policy to allow the agent to occasionally not to take the optimal action according to its experience.
* Discount = Gamma, this models the fact that future rewards are worth less than immediate rewards
* Alpha = Learning rate, set between 0 and 1. Setting it to 0 means that the Q-values are never updated, hence nothing is learned. Setting a high value such as 0.9 means that learning can occur quickly.
* Agent money = Starting capital for AI Agent

# Requirements
 
| Software     | Version         | 
| ------------- |:-------------:| 
| Java      | 1.8.0.131 | 
| jfreechart     | 1.5.0  | 
| Apache commons-csv     | 1.5 |
| Apache commons-math3     | 3.6.1 |

* Some stock market data (preferably 5+ stocks)

# Useful links

Yahoo Finance for csv data : [Yahoo Finance](https://finance.yahoo.com/)

# Whats missing from project

* Proper data saving from GUI
* Ability to clear all data and start over (Now you need to restart program for that)
* Easy way to follow what AI does
* Code refactoring
* Unit tests
* Changeable settings from GUI
* Better exception handling

# Team

* Leif Salminen   
* Eero Salomies   
* Antti Ryökkynen  
* Mokhtar Paukkio 
* Niko Pieviläinen
