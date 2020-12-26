import logging

logging.basicConfig(filename="logfile.log", encoding='utf-8', level=logging.INFO)

def pytest_tavern_beta_after_every_response(expected, response):
    logging.info("=====< START TEST FILE >=====")
    logging.info("===== RESPONSE ======")
    logging.info(response)
    logging.info(response.text)
    logging.info(response.json)

    logging.info("===== EXPECTED ======")
    logging.info(expected)
    logging.info("=====< END TEST FILE >=====")
   