# TestNord.Codes
тестовое задание  Nord.Codes
<a href="https://codeclimate.com/github/adm1341/TestNord.Codes/maintainability"><img src="https://api.codeclimate.com/v1/badges/cbae9850aef3f7a29516/maintainability" /></a>
<a href="https://codeclimate.com/github/adm1341/TestNord.Codes/test_coverage"><img src="https://api.codeclimate.com/v1/badges/cbae9850aef3f7a29516/test_coverage" /></a>
<br>
End points <br>
/users - Управление пользователями.
<br>
/short - Создание и отображение коротких ссылок. <br>
 POST: /short - Создание короткой ссылки возвращает короткую ссылку. <br>
 GET: /{shortUrl} - По существующей короткой ссылке возвращает сайт.<br>
 GET: /{shortUrl}/info - Доступна только Администраторам. Возвращает представление объекта в котором есть количество переходов <br>
 DELETE: /{shortUrl} - Доступна только Администраторам. Удаляет короткую ссылку.<br>