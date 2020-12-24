# tavern

This repository contains tutorials and examples of [Tavern API testing](https://tavern.readthedocs.io/en/latest/index.html) framework.

## Installation

Tavern requires at least **Python 3.4**.

### Python virtual enviroment

If you don't want to install dependencies on you system, you can use Tavern along with Python `virtualenv`.

```python
python -m venv tavern-venv
```

Basically, you'll find a new folder (in the path where you executed the command) named as *tavern-venv* (choose the name that best suits you).

Now, you need to activate the environment. To do so, execute the following command in the same path where you exectued the previous command:

```python
source tavern-venv/bin/activate
```

If the enviroment is running, in your terminal you should see the name you choose after every command:
![Schermata 2020-12-19 alle 11 25 05](https://user-images.githubusercontent.com/503447/102687306-635d1a80-41ee-11eb-8a86-4e9db75cb46a.png)



Now, you just need to install `tavern`:

```python
pip install wheel tavern
```

## Running Tavern tests
Tavern tests are defined in `.yaml` files. There are several ways to run this tests:
* via python
* via command line
* via pytest

I'll show the latter case, using pytest enviroment.

To run a test via pytest, just run pytest and point it towards the integration test folder. It will automatically find the tests via pytests collection mechanism:

![Schermata 2020-12-24 alle 08.31.59](/Users/rik/Desktop/Schermata 2020-12-24 alle 08.31.59.png)


## References

[Tavern docs](https://tavern.readthedocs.io/en/latest/index.html#)

[Tavern repository](https://github.com/taverntesting/tavern/blob/master/docs/source/index.md)

