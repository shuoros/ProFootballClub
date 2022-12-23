<template>
  <div>
    <b-dropdown-item to="/admin">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.admin')">Admin</span>
    </b-dropdown-item>
    <b-dropdown-item to="/coach">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.coach')">Coach</span>
    </b-dropdown-item>
    <b-dropdown-item to="/team">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.team')">Team</span>
    </b-dropdown-item>
    <b-dropdown-item to="/stadium">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.stadium')">Stadium</span>
    </b-dropdown-item>
    <b-dropdown-item to="/player">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.player')">Player</span>
    </b-dropdown-item>
    <b-dropdown-item to="/train">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.train')">Train</span>
    </b-dropdown-item>
    <b-dropdown-item to="/match">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.match')">Match</span>
    </b-dropdown-item>
    <b-dropdown-item to="/composition">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.composition')">Composition</span>
    </b-dropdown-item>
    <b-dropdown-item to="/player-arrange">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.playerArrange')">Player Arrange</span>
    </b-dropdown-item>
    <b-dropdown-item to="/transfer">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.transfer')">Transfer</span>
    </b-dropdown-item>
    <b-dropdown-item to="/league">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.league')">League</span>
    </b-dropdown-item>
    <b-dropdown-item to="/league-board">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.leagueBoard')">League Board</span>
    </b-dropdown-item>
    <b-dropdown-item to="/cup">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.cup')">Cup</span>
    </b-dropdown-item>
    <b-dropdown-item to="/cup-board">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.cupBoard')">Cup Board</span>
    </b-dropdown-item>
    <b-dropdown-item to="/news-paper">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.newsPaper')">News Paper</span>
    </b-dropdown-item>
    <b-dropdown-item to="/friend-request">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.friendRequest')">Friend Request</span>
    </b-dropdown-item>
    <b-dropdown-item to="/ticket">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.ticket')">Ticket</span>
    </b-dropdown-item>
    <b-dropdown-item to="/private-chat">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.privateChat')">Private Chat</span>
    </b-dropdown-item>
    <b-dropdown-item to="/public-chat">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.publicChat')">Public Chat</span>
    </b-dropdown-item>
    <b-dropdown-item to="/message">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.message')">Message</span>
    </b-dropdown-item>
    <b-dropdown-item to="/variables">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.variables')">Variables</span>
    </b-dropdown-item>
    <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
  </div>
</template>

<script lang="ts" src="./entities-menu.component.ts"></script>
