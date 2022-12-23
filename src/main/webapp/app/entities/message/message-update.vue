<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="proFootballClubApp.message.home.createOrEditLabel"
          data-cy="MessageCreateUpdateHeading"
          v-text="$t('proFootballClubApp.message.home.createOrEditLabel')"
        >
          Create or edit a Message
        </h2>
        <div>
          <div class="form-group" v-if="message.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="message.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.message.message')" for="message-message">Message</label>
            <input
              type="text"
              class="form-control"
              name="message"
              id="message-message"
              data-cy="message"
              :class="{ valid: !$v.message.message.$invalid, invalid: $v.message.message.$invalid }"
              v-model="$v.message.message.$model"
              required
            />
            <div v-if="$v.message.message.$anyDirty && $v.message.message.$invalid">
              <small class="form-text text-danger" v-if="!$v.message.message.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.message.coach')" for="message-coach">Coach</label>
            <select class="form-control" id="message-coach" data-cy="coach" name="coach" v-model="message.coach">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="message.coach && teamOption.id === message.coach.id ? message.coach : teamOption"
                v-for="teamOption in teams"
                :key="teamOption.id"
              >
                {{ teamOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.message.ticket')" for="message-ticket">Ticket</label>
            <select class="form-control" id="message-ticket" data-cy="ticket" name="ticket" v-model="message.ticket">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="message.ticket && ticketOption.id === message.ticket.id ? message.ticket : ticketOption"
                v-for="ticketOption in tickets"
                :key="ticketOption.id"
              >
                {{ ticketOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.message.privateChat')" for="message-privateChat"
              >Private Chat</label
            >
            <select class="form-control" id="message-privateChat" data-cy="privateChat" name="privateChat" v-model="message.privateChat">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  message.privateChat && privateChatOption.id === message.privateChat.id ? message.privateChat : privateChatOption
                "
                v-for="privateChatOption in privateChats"
                :key="privateChatOption.id"
              >
                {{ privateChatOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.message.publicChat')" for="message-publicChat"
              >Public Chat</label
            >
            <select class="form-control" id="message-publicChat" data-cy="publicChat" name="publicChat" v-model="message.publicChat">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="message.publicChat && publicChatOption.id === message.publicChat.id ? message.publicChat : publicChatOption"
                v-for="publicChatOption in publicChats"
                :key="publicChatOption.id"
              >
                {{ publicChatOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.message.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./message-update.component.ts"></script>
