<html>

<body>
<div>
    <form method="post">
        <input type="text" name="name" placeholder="Введите название здания" />
        <input type="text" name="address" placeholder="Адрес">
        <input type="text" name="description" placeholder="Описание">
        <input type="number" name="stages" placeholder="Количество этажей">
        <button type="submit">Добавить</button>
    </form>
</div>
<div>Список зданий</div>
<form method="post" action="filter">
    <input type="text" name="filter">
    <button type="submit">Найти</button>
</form>
{{#buildings}}
    <div>
        <b>{{id}}</b>
        <span>{{name}}</span>
        <span>{{address}}</span>
        <span>{{description}}</span>
        <span>{{address}}</span>
    </div>
{{/buildings}}
</body>
</html>