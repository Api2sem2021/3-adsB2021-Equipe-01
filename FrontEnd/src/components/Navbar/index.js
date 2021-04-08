import React, { useState } from 'react';

import { Menu, Button } from 'antd';

import { Link } from 'react-router-dom';

import { FiHome, FiPhone, FiUsers, FiHelpCircle } from 'react-icons/fi';
import * as styles from './styles';

export function Navbar() {
  const [current, setCurrent] = useState('home');

  function handleMenuClick(e) {
    setCurrent(e.key);
  }

  return (
    <styles.Container>
      <styles.Content>
        <styles.MenuNavBarLeft>
          <Menu
            onClick={handleMenuClick}
            selectedKeys={[current]}
            mode="horizontal"
          >
            <Menu.Item key="home" icon={<FiHome />}>
              Home
            </Menu.Item>

            <Menu.Item key="sobre" icon={<FiHelpCircle />}>
              Sobre
            </Menu.Item>

            <Menu.Item key="quem somos" icon={<FiUsers />}>
              Quem Somos?
            </Menu.Item>

            <Menu.Item key="contato" icon={<FiPhone />}>
              Contato
            </Menu.Item>
          </Menu>
        </styles.MenuNavBarLeft>
        <styles.MenuNavBarRight>
          <Button type="link">
            <Link to="/signin">Entrar</Link>
          </Button>

          <Button type="primary">
            <Link to="/signup">Cadastrar</Link>
          </Button>
        </styles.MenuNavBarRight>
      </styles.Content>
    </styles.Container>
  );
}
