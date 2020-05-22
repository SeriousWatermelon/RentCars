<nav style="margin: 10px auto 50px auto ;">
    <ul class="nav nav-tabs">
        <li id="nav_home">
            <a href="/customer/home/index">Home</a>
        </li>
        <li id="nav_cars">
            <a href="/customer/home/cars">Cars Information</a>
        </li>
        <li id="nav_introduce">
            <a href="/customer/home/introduce">introduction</a>
        </li>
        <li id="nav_record">
            <a href="/customer/home/history?id=${user.id}">Vehicle Reservation Record</a>
        </li>
        <li id="nav_customer_info">
            <a href="/customer/home/user?id=${user.id}">${user.username}</a>
        </li>
        <li class="dropdown pull-right">
            <a href="/logout">LogOut</a>
        </li>
        <li class="dropdown pull-right">
            <a>Contact Us By 022-336655</a>
        </li>
    </ul>
</nav>