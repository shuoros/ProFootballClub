<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="player">
        <h2 class="jh-entity-heading" data-cy="playerDetailsHeading">
          <span v-text="$t('proFootballClubApp.player.detail.title')">Player</span> {{ player.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('proFootballClubApp.player.firstName')">First Name</span>
          </dt>
          <dd>
            <span>{{ player.firstName }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.player.lastName')">Last Name</span>
          </dt>
          <dd>
            <span>{{ player.lastName }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.player.gender')">Gender</span>
          </dt>
          <dd>
            <span v-text="$t('proFootballClubApp.Gender.' + player.gender)">{{ player.gender }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.player.country')">Country</span>
          </dt>
          <dd>
            <span v-text="$t('proFootballClubApp.Country.' + player.country)">{{ player.country }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.player.age')">Age</span>
          </dt>
          <dd>
            <span>{{ player.age }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.player.status')">Status</span>
          </dt>
          <dd>
            <span v-text="$t('proFootballClubApp.PlayerStatus.' + player.status)">{{ player.status }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.player.post')">Post</span>
          </dt>
          <dd>
            <span v-text="$t('proFootballClubApp.Post.' + player.post)">{{ player.post }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.player.totalPower')">Total Power</span>
          </dt>
          <dd>
            <span>{{ player.totalPower }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.player.goalkeeping')">Goalkeeping</span>
          </dt>
          <dd>
            <span>{{ player.goalkeeping }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.player.defence')">Defence</span>
          </dt>
          <dd>
            <span>{{ player.defence }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.player.tackling')">Tackling</span>
          </dt>
          <dd>
            <span>{{ player.tackling }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.player.passing')">Passing</span>
          </dt>
          <dd>
            <span>{{ player.passing }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.player.teamSkill')">Team Skill</span>
          </dt>
          <dd>
            <span>{{ player.teamSkill }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.player.ballSkill')">Ball Skill</span>
          </dt>
          <dd>
            <span>{{ player.ballSkill }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.player.shooting')">Shooting</span>
          </dt>
          <dd>
            <span>{{ player.shooting }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.player.longShooting')">Long Shooting</span>
          </dt>
          <dd>
            <span>{{ player.longShooting }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.player.dribbling')">Dribbling</span>
          </dt>
          <dd>
            <span>{{ player.dribbling }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.player.technique')">Technique</span>
          </dt>
          <dd>
            <span>{{ player.technique }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.player.confidence')">Confidence</span>
          </dt>
          <dd>
            <span>{{ player.confidence }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.player.team')">Team</span>
          </dt>
          <dd>
            <div v-if="player.team">
              <router-link :to="{ name: 'TeamView', params: { teamId: player.team.id } }">{{ player.team.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="player.id" :to="{ name: 'PlayerEdit', params: { playerId: player.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./player-details.component.ts"></script>
